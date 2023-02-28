

function uiCalendar(events){

    var calendarEl=document.getElementById('calendar');
//    var calendarEl=document.getElementById('week');

    var calendar=new FullCalendar.Calendar(calendarEl,{
        timeZone:'UTC',
        themeSystem:'bootstrap5',
        customButtons: {
            createReservation:{
                text: 'Create Reservation',
                click:function(mouseEvent, htmlElement){
                    window.location.href = '/reservations/create'
                }
            },
            reservationTable:{
                            text: 'Grid View',
                            click:function(mouseEvent, htmlElement){
                                window.location.href = '/reservations/grid'
                            }
                        }
        },
        headerToolbar:{
            left:'createReservation reservationTable',
            center:'title',
            right:'dayGridMonth,timeGridWeek,timeGridDay prev,next today'},
        events:events,
        dayMaxEvents:true,//allow"more"link when too many events,
    });

    calendar.render();
}