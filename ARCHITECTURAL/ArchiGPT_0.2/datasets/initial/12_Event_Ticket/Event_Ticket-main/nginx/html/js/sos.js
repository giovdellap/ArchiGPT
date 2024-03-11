const pulsante = document.getElementById("but-sos");
const sot= document.getElementById('father-sos');

pulsante.addEventListener('mouseenter', e => {
      pulsante.style.backgroundColor='#B00000';
      pulsante.style.color='# B00000';
      pulsante.style.fontWeight='Bolder';
      pulsante.style.boxShadow='1px  1px 4px #B00000';
      
      sot.style.color='#B00000';
      sot.style.textShadow='1px 1px 4px #B00000';
      

});
    
pulsante.addEventListener('mouseleave', e => {
      pulsante.style.backgroundColor='#78dbb5';
      pulsante.style.color='#04044c';
      pulsante.style.fontWeight='Bold';
      pulsante.style.boxShadow='1px  1px 4px #4e4e64';

      sot.style.color='#78dbb5';
      sot.style.textShadow='none';
});