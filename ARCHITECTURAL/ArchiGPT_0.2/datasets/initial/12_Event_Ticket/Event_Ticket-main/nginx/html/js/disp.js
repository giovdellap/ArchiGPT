$(document).ready(function () {
  
    var show=0;
    //controlla data fra il 2020-2030
    var data_valid =/^(\d{1,2})\/(\d{1,2})\/((202)\d{1})$/;
   
$('#disp').click(function(){
        var date= document.getElementById("reservationDate").value;
        var dataselezionata= document.getElementById("pickedDate");
        dataselezionata.innerHTML=date;
        
        
        if(show==0){
            
            if (!$("#reservationDate").val() || (!data_valid.test(date))){
            alert("Seleziona una data valida prima di continuare");
            }
    
            else{
            $('#disp-panel').show();
            show=1;
            }

        }  

        else{
            $('#disp-panel').hide();
            show=0;
            dataselezionata.innerHTML="NON SELEZIONATA";
      }  
    });
});





    