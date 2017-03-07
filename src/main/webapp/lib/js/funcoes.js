		PrimeFaces.locales['pt'] = {  
		        closeText: 'Fechar',  
		        prevText: 'Anterior',  
		        nextText: 'Próximo',  
		        currentText: 'Começo',  
		        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
		        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],  
		        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
		        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],  
		        dayNamesMin: ['D','S','T','Q','Q','S','S'],  
		        weekHeader: 'Semana',  
		        firstDay: 1,  
		        isRTL: false,  
		        showMonthAfterYear: false,  
		        yearSuffix: '',  
		        timeOnlyTitle: 'Só Horas',  
		        timeText: 'Tempo',  
		        hourText: 'Hora',  
		        minuteText: 'Minuto',  
		        secondText: 'Segundo',  
		        currentText: 'Data Atual',  
		        ampm: false,  
		        month: 'Mês',  
		        week: 'Semana',  
		        day: 'Dia',  
		        allDayText : 'Todo Dia'
		    };
/**
 * @param data
 * @returns {String}
 */
function dateToBr(data){
	var vlrUSA = data.split("-");
	var vlrBR = (vlrUSA[2]+"/"+vlrUSA[1]+"/"+vlrUSA[0]);
	return vlrBR;
} 

/**
 * Example: path("/MyServlet")
 * @param txt
 * @returns
 */
function path(txt){
	var myPath = $("#contextPath").html()+txt;
	console.log(myPath);
	return myPath;
}