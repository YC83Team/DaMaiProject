	//打开修改窗口
	function openadd(index) {
		$("#dialogmodify").dialog('open');
		var rows = $('#dg').datagrid("getRows");
		$('#modifyform').form('load',rows[index]);
		//禁用id输入框
		$("#id").textbox({disabled:true});
	}
	
	//取消修改
	function canceladd(){
		$("#foradd").dialog('close');
	}
	
	//添加
	function add(){
		//启用id输入框，否则id值无法传给后台
		$("#id").textbox({disabled:false});
		//询问是否添加
		$.messager.confirm('Confirm', '您确定要保存吗?', function(r){
			if (r){
				$('#modifyform').form('submit',{
					url:"../bkorder.do",
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
							$("#dialogmodify").dialog('close');
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