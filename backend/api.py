from flask import Flask, render_template, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from flask_security import current_user, login_required, RoleMixin, Security, SQLAlchemyUserDatastore, UserMixin, utils
from flask_admin.contrib.sqla import ModelView
from flask_admin import Admin
from flask_admin.contrib.sqla import fields
from flask_admin._compat import text_type
from sqlalchemy.orm.util import identity_key
import json
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
app.config['DEBUG'] = True
app.config['SECRET_KEY'] = 'UdDs4haqPted9aP3gz6kfvHKpCwxe7AY' # Change!
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///test.db'
app.config['SECURITY_PASSWORD_HASH'] = 'pbkdf2_sha512' # Alternatively, bcrypt
app.config['SECURITY_PASSWORD_SALT'] = '4pK3bUxRzkDWGGb9serLmmVVRvPjaGu4' # Change!
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)


roles_users = db.Table(
    'roles_users',
    db.Column('user_id', db.Integer(), db.ForeignKey('user.id')),
    db.Column('role_id', db.Integer(), db.ForeignKey('role.id'))
)

# Monkey-Patch for messed up flask-admin release
def get_pk_from_identity(obj):
    res = identity_key(instance=obj)
    cls, key = res[0], res[1]
    return u':'.join(text_type(x) for x in key)

fields.get_pk_from_identity = get_pk_from_identity

class Role(db.Model, RoleMixin):
    id = db.Column(db.Integer(), primary_key=True)
    name = db.Column(db.String(80), unique=True)
    description = db.Column(db.String(255))

    def __str__(self):
        return self.name

    def __hash__(self):
        return hash(self.name)


class User(db.Model, UserMixin):
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(255), unique=True)
    password = db.Column(db.String(255))
    prename = db.Column(db.String(255))
    surname = db.Column(db.String(255))
    score = db.Column(db.Integer())
    active = db.Column(db.Boolean())
    confirmed_at = db.Column(db.DateTime())
    roles = db.relationship(
        'Role',
        secondary=roles_users,
        backref=db.backref('users', lazy='dynamic')
    )


class Article(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    category = db.Column(db.String()) # Should be a 1-x thing
    timestamp = db.Column(db.BigInteger())
    headline = db.Column(db.String())
    abstract = db.Column(db.String())
    lat = db.Column(db.Float())
    lng = db.Column(db.Float())
    content = db.Column(db.String()) # will become 1-x
    quiz_id = db.Column(db.Integer, db.ForeignKey("quiz.id"))
    quiz = db.relationship("Quiz", uselist=False)
    background_information_id = db.Column(db.Integer, db.ForeignKey("information.id"))
    background_information = db.relationship("Information", uselist=False)
    category_id = db.Column(db.Integer, db.ForeignKey("category.id"))
    category = db.relationship("Category", uselist=False)

    def __str__(self):
        return self.headline


class Category(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    description = db.Column(db.String)
    article = db.relationship("Article", uselist=False, back_populates="category")
    #information = db.relationship("Information", uselist=False, back_populates="category")


    def __str__(self):
        return self.name


class Information(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    #category_id = db.Column(db.Integer, db.ForeignKey("category.id"))
    #category = db.relationship("Category", uselist=False)
    category = db.Column(db.String())
    timestamp = db.Column(db.BigInteger())
    headline = db.Column(db.String())
    content = db.Column(db.String()) # will become 1-x
    #article = db.relationship("Article", uselist=False, back_populates="information")

    def __str__(self):
        return self.headline


class Quiz(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    question = db.Column(db.String())
    answers = db.Column(db.String())
    solution = db.Column(db.String())
    article = db.relationship("Article", uselist=False, back_populates="quiz")

    def __str__(self):
        return self.question



user_datastore = SQLAlchemyUserDatastore(db, User, Role)
security = Security(app, user_datastore)

class RoleAdmin(ModelView):
    def is_accessible(self):
        return current_user.has_role('admin')


admin = Admin(app, template_mode='bootstrap3')
#admin.add_view(RoleAdmin(Role, db.session))
admin.add_view(ModelView(User, db.session))
admin.add_view(ModelView(Role, db.session))
admin.add_view(ModelView(Article, db.session))
admin.add_view(ModelView(Information, db.session))
admin.add_view(ModelView(Quiz, db.session))
admin.add_view(ModelView(Category, db.session))


@app.before_first_request
def before_first_request():

    db.create_all()

    user_datastore.find_or_create_role(name='admin', description='Administrator')
    user_datastore.find_or_create_role(name='end-user', description='User')

    encrypted_password = utils.encrypt_password('password')
    if not user_datastore.get_user('admin@kim.com'):
        user_datastore.create_user(email='admin@kim.com', password=encrypted_password)
    if not user_datastore.get_user('user@kim.com'):
        user_datastore.create_user(email='user@kim.com', password=encrypted_password)
    db.session.commit()

    user_datastore.add_role_to_user('admin@kim.com', 'end-user')
    user_datastore.add_role_to_user('user@kim.com', 'admin')
    db.session.commit()


def content_to_list(content):
    """Not production ready!; Needs to be replaced"""
    content = content.split('###')
    content_list = []
    for piece in content:
        content_type, content_data = piece.split('!!!')
        content_list.append((content_type, content_data))

    return content_list


def quiz_answers_to_list(quiz_answers):
    """Not production ready!; Needs to be replaced"""
    answers = quiz_answers.split('###')
    answers_list = []
    for answer in answers:
        answer, answer_correct = answer.split('!!!')
        if answer_correct == 'False':
            answer_correct = False
        else:
            answer_correct = True
        answers_list.append((answer, answer_correct))

    return answers_list


@app.route('/')
#@login_required
def index():
    return 'BlauWow - KIM'


@app.route('/api/user/')
def api_users_list():
    users = User.query.all()
    users_list = [{'id': user.id, 'score': user.score} for user in users]
    return jsonify(users_list)


@app.route('/api/article/')
def api_articles_list():
    articles = Article.query.all()
    articles_list = [{'id': article.id, 'headline': article.headline, 'category': article.category.name} for article in articles]
    return jsonify(articles_list)


@app.route('/api/article/full_list')
def api_articles_all():
    articles = Article.query.all()
    articles_list = []

    for article in articles:
        articles_list.append({'id': article.id, 'headline': article.headline, 'content': content_to_list(article.content),
                    'category': article.category.name, 'timestamp': article.timestamp, 'background_information_id': article.background_information_id,
                    'quiz_id': article.quiz_id, 'lat': article.lat, 'lng': article.lng, 'abstract': article.abstract})

    return jsonify(articles_list)


@app.route('/api/information/')
def api_information_list():
    information = Information.query.all()
    information_list = [{'id': info.id, 'headline': info.headline} for info in information]
    return jsonify(information_list)


@app.route('/api/user/<int:user_id>/score/', methods=['GET', 'POST'])
#@login_required
def api_score(user_id):
    if request.method == 'GET':
        user_score = User.query.get(user_id).score
        return jsonify({'user_id': user_id, 'score': user_score})
    if request.method == 'POST':
        user = User.query.get(user_id)
        new_score = int(request.form['score'])
        user.score = new_score
        db.session.commit()
        return json.dumps({'success':True}), 200, {'ContentType':'application/json'} 


@app.route('/api/article/<int:article_id>/', methods=['GET'])
def api_article(article_id):
    article = Article.query.get(article_id)
    article_dict = {'id': article.id, 'headline': article.headline, 'content': content_to_list(article.content),
                    'category': article.category.name, 'timestamp': article.timestamp, 'background_information_id': article.background_information_id,
                    'quiz_id': article.quiz_id, 'abstract': article.abstract}
    return jsonify(article_dict)


@app.route('/api/information/<int:information_id>/', methods=['GET'])
def api_information(information_id):
    information = Information.query.get(information_id)
    information_dict = {'id': information.id, 'headline': information.headline, 'content': content_to_list(information.content), 'timestamp': information.timestamp}
    return jsonify(information_dict)


@app.route('/api/quiz/<int:quiz_id>/', methods=['GET'])
def api_quiz(quiz_id):
    quiz = Quiz.query.get(quiz_id)
    quiz_dict = {'id': quiz.id, 'question': quiz.question, 'answers': quiz_answers_to_list(quiz.answers), 'solution': quiz.solution}
    return jsonify(quiz_dict)


@app.errorhandler(404)
def page_not_found(e):
    return render_template('404.html'), 404

if __name__ == '__main__':
    app.run(
        host='0.0.0.0',
        port=int('8080'),
        debug=app.config['DEBUG'],
        threaded=True
    )