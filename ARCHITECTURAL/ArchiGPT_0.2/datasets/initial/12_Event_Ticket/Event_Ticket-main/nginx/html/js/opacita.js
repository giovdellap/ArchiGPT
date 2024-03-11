var val = 0;

function cambiaOpacita(){
    var immagine = document.getElementById("im");
    
    
    immagine.style.opacity = val/100;
    immagine.style.filter = 'alpha(opacity = ' + val + ')';       
}
function aumentaOpacita() {
    val += 2;  
    cambiaOpacita();
    

    if(val<100){  
        
    setTimeout(aumentaOpacita, 50);
    }  
}
aumentaOpacita();