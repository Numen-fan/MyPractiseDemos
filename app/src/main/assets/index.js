(function() {
    var calc = function() {
        var rem = document.documentElement.clientWidth / 10;
        document.documentElement.style.fontSize = rem + 'px'
    }
    calc();
    window.addEventListener('resize', calc)
})()