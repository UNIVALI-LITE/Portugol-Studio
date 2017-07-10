$(document).ready(function(){
  var height = $( window ).height()
  $('section').css('min-height',height)
})

$(".arrow").click(function(){
  var height = $(this.dataset.to).offset().top
  console.log(this.dataset.to + ' - ' + height)
  $("html, body").animate({ scrollTop:  height}, "ease")
})