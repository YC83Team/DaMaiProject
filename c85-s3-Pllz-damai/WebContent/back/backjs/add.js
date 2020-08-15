	//打开修改窗口
	function openadd(index) {
		$("#dialogadd").dialog('open');
		//禁用id输入框
		$("#id").textbox({disabled:true});
		//index有芝 就是修改表格中得某一行记录，否则是新增
		//edit() 不传参数 index为undfined
		if(index !=undefined){
			var rows=$('#dg').datagrid("getRows");
			rows[index].isHot=rows[index].is_hot;
			rows[index].marketPrice = rows[index].market_price;
			rows[index].shopPrice = rows[index].shop_price;
			if( rows[index].image.indexOf("/")!=0){
				upImg.src = "../"+rows[index].image;
			}else{
				upImg.src = rows[index].image;
			}
			$('#addform').form('load',rows[index]);
		}else{
			$('#addform').form('clear');
			upImg.src = "../images/addimage.jpg";
		}
	}
	
	//添加
	function add(){
		//启用id输入框，否则id值无法传给后台
		$("#id").textbox({disabled:false});
		//询问是否添加
		$.messager.confirm('Confirm', '您确定要保存吗?', function(r){
			if (r){
				$('#addform').form('submit',{
					url:"../product.do",
					onSubmit: function(param){
						//  扩展请求参数
						param.op = "save";
					},
					success:res=>{
						//alert(res);
						//原有的res==》消息，现在的res==》json对象（code，msg，data）
						res=JSON.parse(res);
						$.messager.alert('tip',res.msg);
						if(res.code==1){
							//关闭窗口
							$("#dialogadd").dialog('close');
							//刷新表格
							 $('#dg').datagrid("reload");
							
						}
						//否则什么也不做
					}
				});
			
			}
		
		})
	}
	
	
	//取消修改
	function canceladd(){
		$("#foradd").dialog('close');
	}