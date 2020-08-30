let stompClient = null;

const answerInputPlaceholder = 'answer-input-';
const answerButtonPlaceholder = 'answer-button-';
const trPlaceholder = 'tr-';

const websocketUrl = '/api/game-ws';
const topicEquation = '/api/topic/equation';
const topicResult = '/api/topic/result';
const topicAnswer = '/api/topic/answer';

const gameUrl = '/game';

const setConnected = (connected) => {
    if (connected) {
        $("#game-start").hide();
        $("#game-process").show();
    } else {
        $("#game-start").show();
        $("#game-process").hide();
    }
}

const start = (sessionId) => {
    stompClient = Stomp.over(new SockJS(websocketUrl));
    stompClient.connect({}, (frame) => {
        const number = $("#number-input").val()
        console.log("start: number = ", number)
        setConnected(true);
        console.log('Connected: ' + frame);
        const gameId = sessionId + Date.now();
        console.log("start: gameId = ", gameId)
        stompClient.subscribe(`${topicEquation}.${gameId}`, (equation) => showEquation(JSON.parse(equation.body)));
        stompClient.subscribe(`${topicResult}.${gameId}`, (result) => showResult(JSON.parse(result.body)));
        fetch(`${gameUrl}/${sessionId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                'sessionId': sessionId,
                'gameId': gameId,
                'number': number
            })
        });
    });
}

// Expected values in result:
// gameId : String
// numberOfSuccess : Integer
// numberOfAll : Integer
const showResult = (result) => {
    console.log("show result = ", result)
    $("label-result").text(`Количество правильных ответов ${result.numberOfSuccess} из ${result.numberOfAll}`)
    if (result.numberOfSuccess === result.numberOfAll) {
        stompClient.unsubscribe(`${topicEquation}.${result.gameId}`);
        stompClient.unsubscribe(`${topicResult}.${result.gameId}`);
        disconnect()
    }
}

// Expected values in equation:
// gameId : String
// eqId : Integer
// eqText : String
const showEquation = (equation) => {
    console.log("show equation = ", equation);
    const answerInputId = answerInputPlaceholder + equation.eqId;
    const answerButtonId = answerButtonPlaceholder + equation.eqId;
    const trId = trPlaceholder + equation.eqId;
    $("#game-equation")
        .append(`<tr id='${trId}'><th>${equation.eqText}</th><td><input id='${answerInputId}' type='number' min='0' max='100' value=''></td><td><input id='${answerButtonId}' type='submit' value='Ответить' onclick='sendAnswer(equation)'></td></tr>`)
}

const sendAnswer = (equation) => {
    const answerInput = $(`#${answerInputPlaceholder}${equation.eqId}`);
    const answer = answerInput.val();
    console.log("send answer = ", answer);

    answerInput.parent().remove();
    $(`#${answerButtonPlaceholder}${equation.eqId}`).parent().remove();
    $(`#${trPlaceholder}${equation.eqId}`).append(`<td><label>${answer}</label></td>`);

    stompClient.send(`${topicAnswer}.${equation.gameId}`, {}, JSON.stringify({
        'gameId': equation.gameId,
        'eqId': equation.eqId,
        'answer': answer
    }))
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    setConnected(false);
});
