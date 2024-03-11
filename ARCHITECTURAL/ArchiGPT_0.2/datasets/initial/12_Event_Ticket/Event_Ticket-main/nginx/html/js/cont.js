
const box1 = document.getElementById('cont1');
const box2 = document.getElementById('cont2');
const after1 = document.getElementById('show-contatti1');
const after2 = document.getElementById('show-contatti2');
const before1 = document.getElementById('cover-contatti1');
const before2 = document.getElementById('cover-contatti2');

box1.addEventListener('mouseenter', e => {
    before1.style.display='none';
    after1.style.display='block';
});

box2.addEventListener('mouseenter', e => {
    before2.style.display='none';
    after2.style.display='block';
});