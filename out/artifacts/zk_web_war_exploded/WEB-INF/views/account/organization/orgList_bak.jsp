<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" style="padding:5px">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>组织机构树</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="50" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder" oncheck="kkk">
						<li value="11"><a href="${ctx}/account/user" target="ajax" rel="jbsxBox" tname="name" tvalue="value0">实验室检测</a>
							<ul>
								<li ><abbr value="12"><a href="${ctx}/account/user" target="ajax" rel="jbsxBox" tname="name1" tvalue="value1" checked="true">尿检</a></abbr></li>
								<li value="13"><a href="demo/pagination/list1.html" target="ajax" rel="jbsxBox" tname="name" tvalue="value2">HIV检测</a></li>
								<li><a href="demo/pagination/list1.html" target="ajax" rel="jbsxBox" tname="name" tvalue="value3">HCV检测</a></li>
								<li><a href="demo/pagination/list1.html" target="ajax" rel="jbsxBox" tname="name" tvalue="value4">TB检测</a></li>
								<li><a href="javascript:;" tname="name" tvalue="value5">有提示的表单输入页面</a>
									<ul>
										<li><a href="javascript:;" tname="name" tvalue="value6">页面一</a></li>
										<li><a href="javascript:;" tname="name" tvalue="value7">页面二</a></li>
									</ul>
								</li>
							</ul>
						</li>
						
				     </ul>
				</div>
				<div id="jbsxBox" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
				</div>
			</div>
		</div>
		
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$("abbr").contextMenu('treeCM', {
		      bindings:{
		    	  	addOrg:function(t){
		    	  	  var id = t.context.value;
	                  var url = "${ctx}/account/organization/toAddSubOrgPage/"+id;
	                  var options = "{width:100px,height:100px,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true}";
		    	  	  $.pdialog.open(url, "TEST1234", "添加下级机构",options);
		            },
		            editOrg:function(t){
		                  // TODO
	
		            },
		            delOrg:function(t){
		                  // TODO
	
		            }
		      },
		      ctrSub:function(t,m){
		            var addInfo = m.find("[rel='addOrg']");
	
		            var editInfo = m.find("[rel='editOrg']");
	
		            var delInfo = m.find("[rel='delOrg']");
		            // TODO
		      }
		});
	});

	function kkk(){
		var json = arguments[0];
		var result="";
		alert(json.checked);
		/* $(json.items).each(function(i){
			result += "<p>name:"+this.name + " value:"+this.value+" text: "+this.text+"</p>";
		});
		//$("#resultBox").html(result);
		alert(result); */
	}
	
	function kkk2(){
		/*   $("[name='name1']").removeAttr("checked");//取消全选 */
		  
			/* $("[name='name']").attr("checked",'true');//全选 */
		

		$("input:checked").each(function(i,a){
			 alert(a.value); 
		});
	}
	
</script>
