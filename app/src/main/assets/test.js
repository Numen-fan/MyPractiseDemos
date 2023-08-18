 function callAndroid(){
    console.log("发起调用");
    alert("调用了啊");
    ap.call('tinyToNative', {
        param1: 'p1aaa',
        param2: 'p2bbb'
    }, (result) => {
    console.log(result);
    ap.showToast({
    type: 'none',
    content: result.message,
    duration: 3000,
    });
   })
 }