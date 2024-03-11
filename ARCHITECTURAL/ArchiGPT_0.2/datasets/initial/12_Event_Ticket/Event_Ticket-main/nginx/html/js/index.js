//setting zoom default
document.body.style.zoom="105%";

//navbar effects
const navSlide=() => {
        const burger= document.querySelector('.burger');
        const nav =document.querySelector('.ul-nav');
        const navLinks = document.querySelectorAll('.ul-nav li');

        navLinks.forEach((li,index)=> {
                li.addEventListener('mouseenter', e => {
                        li.style.textShadow='2px 2px  5px #4e4e64';
                });
                li.addEventListener('mouseleave', e => {
                        li.style.textShadow='none';
                  });
        });

        burger.addEventListener('click',()=> {
                nav.classList.toggle('nav-active');
        
                navLinks.forEach((link,index)=> {
                        
                        if (link.style.animation) {
                                link.style.animation='';
                        }
                        else {
                               link.style.animation= 'navLinkFade 0.5s ease forwards ${index / 7+1.5}s';
                        }
                });
        burger.classList.toggle('toggle');
        });
}

navSlide();

const card= document.querySelectorAll('.card');
const cardHome= document.querySelectorAll('.card-home');
const cardPedi= document.querySelectorAll('.pedi')

card.forEach((c,index)=> {
        c.addEventListener('mouseenter', e => {
                c.style.boxShadow='5px 5px  5px #78dbb5';
        });
        c.addEventListener('mouseleave', e => {
                c.style.boxShadow='5px 5px 4px #4e4e64';
          });
});

cardHome.forEach((ch,index)=> {
        ch.addEventListener('mouseenter', e => {
                ch.style.boxShadow='5px 5px  5px #78dbb5';
        });
        ch.addEventListener('mouseleave', e => {
                ch.style.boxShadow='5px 5px 4px #4e4e64';
          });
});

cardPedi.forEach((p,index)=> {
        p.addEventListener('mouseenter', e => {
               p.style.boxShadow='5px 5px  5px #78dbb5';
        });
        p.addEventListener('mouseleave', e => {
                p.style.boxShadow='5px 5px 4px #4e4e64';
          });
});


