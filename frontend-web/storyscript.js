
var articlejson;
$(document).ready(function(){
        $.getJSON("http://10.52.1.114:8080/api/article/full_list", function(json){
            articlejson = json
            console.log(articlejson)
            addcontent(1, articlejson)
        });
});


var storyid = "<?php echo $storyid ?>";


function addcontent(storyid,jsonarticle){
  document.getElementById("headline").innerHTML = jsonarticle[storyid-1].headline
  for (i=0;i<jsonarticle[storyid-1].content.length;i++){
    if(jsonarticle[storyid-1].content[i][0] = "img"){
      document.getElementById("content").innerHTML += '<img src='+jsonarticle[storyid-1].content[i][1].toString()+'>'
    }
    else if(jsonarticle[storyid-1].content[i][0] = "video"){
      document.getElementById("content").innerHTML += '<embed src='+jsonarticle[storyid-1].content[i][1].toString()+'>'
    }
    else{
    document.getElementById("content").innerHTML += '<div class="t">'+jsonarticle[storyid-1].content[i][1]+'</div>'
    }
  }
}
/*document.getElementById("headline").innerHTML = jsonarticle.headline
document.getElementById("bg").innerHTML = jsonarticle.background_information_id

document.getElementById("quiz").innerHTML = jsonarticle.quiz_id

for (item in jsonarticle[storyid-1].content){
  document.getElementById("content").innerHTML = '<div class="col-sm-2" id="a"></div><div class="col-sm-8" id="b">'+item[1]+'</div><div class="col-sm-2" id="c"></div>'item[1]
}

*/
