# ToDo

- Add additional table for content pieces
- Add (enable) authentication
- Mark seen information pieces as seen

# Content Format

Currently, content needs to be provided in a rather strange format:

    content_type!!!content###content_type!!!content###content_type!!!content

This will then be split up into tuples (type, content). Currently the followings types are supported: img, text, video (YouTube Link)
