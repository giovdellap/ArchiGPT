$(document).ready(function(){
    $(".pedi-img-js").hover(function(){
        $(this).text("clicca per qui visualizzare la bio");
    },
    function(){
        $(this).text("");
    });
});