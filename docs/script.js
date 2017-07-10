var is = false
var op = 1
$(document).ready(function(){
  var height = $( window ).height()
  $('section').css('min-height',height)
  swapC()
})

function swapC() {
    $('.arrow').animate({ opacity: op }, 1000)
    if(is){
      op = 1
    }else{
      op = 0.5
    }
    is = !is
    window.setTimeout(function() { swapC() }, 1000)
}

$(".goto").click(function(){
  var height = $(this.dataset.to).offset().top
  console.log(this.dataset.to + ' - ' + height)
  $("html, body").animate({ scrollTop:  height}, "ease")
})