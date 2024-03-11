carello = document.getElementById("dropdownMenu2")
header = document.getElementById("header")
carello.addEventListener("mouseover", function( event ) {
    // highlight the mouseenter target
    setTimeout(function() {
        event.target.click()
      }, 600);
    
    
  }, false);


function resetWhenLeave(event,obj){
    obj.addEventListener("mouseleave",function(event2){
        event.target.click()
    },false)
}

