var isSetup = true;
var placedShips = 0;
var sonarOn = false;
var sonarsUsed = 0;
var game;
var shipType;
var vertical;

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK")
            className = "hit";
        else if (attack.result === "SURRENDER")
           { className = "hit";
             let sweeper = document.getElementById("place_minesweeper");
             sweeper.classList.remove('hidden');
             let destroyer= document.getElementById("place_destroyer");
             destroyer.classList.remove('hidden');
             let battleship= document.getElementById("place_battleship");
             battleship.classList.remove('hidden');
             Notify(surrenderText);

            }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");

    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let numSunk = 0;

    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);

    let sweeper = document.getElementById("place_minesweeper");
    let destroyer= document.getElementById("place_destroyer");
    let battleship= document.getElementById("place_battleship");

    let sonar = document.getElementById("place_sonar");

    let playerCont = document.getElementById('playerShips');
    let oppCont = document.getElementById('shipGraveYard');

    let divVBox = document.getElementById('div_vBox');

    let vBox = document.getElementById('is_vertical');

    console.log(col);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, "You can't place that ship there", function(data) {
            game = data;
            redrawGrid();
            placedShips++;
            document.getElementById('place_'+ shipType.toLowerCase()).classList.add('hidden');
            if (placedShips == 3) {
                playerCont.classList.add('hidden');
                vBox.classList.add('hidden');
                divVBox.classList.add('hidden');
                oppCont.classList.remove('hidden');

                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    }
    else if(sonarOn && (sonarsUsed <= 2)){


        document.getElementById('opponent').rows[row].cells[col - 'A'.charCodeAt(0)].classList.add('revealed');
    }
    else{
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, "You can't attack the same square twice", function(data) {
            game = data;
            if (game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].result == "SUNK") {
                Notify("You sunk the opponent's " + game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind);
                numSunk++;
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "minesweeper"){
                    sweeper.classList.remove('selected');
                    sweeper.classList.remove('hidden');
                }
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "destroyer"){
                    destroyer.classList.remove('hidden');
                    desroyer.classList.remove('selected');
                }
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "battleship"){
                    battleship.classList.remove('hidden');
                    battleship.classList.remove('selected');
                }
                if(numSunk == 1){
                    sonar.classList.remove('hidden');
                }
            }
            redrawGrid();
        })
    }
}

function Notify(message) {
    document.getElementById("alert-modal").classList.remove("hidden");
    document.getElementById("alert-content").textContent = message;
    setTimeout(function (){
        document.getElementById("alert-modal").classList.add("hidden");
    }, 2500);
}

function sendXhr(method, url, data, message, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            Notify(message);
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;


        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");

        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);



    document.getElementById("place_sonar").addEventListener("click",function(e){
        sonarOn = true;
    })
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
       shipType = "MINESWEEPER";
       document.getElementById("place_minesweeper").classList.add("selected");
       document.getElementById("place_destroyer").classList.remove("selected");
       document.getElementById("place_battleship").classList.remove("selected");
       registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
       shipType = "DESTROYER";
       document.getElementById("place_minesweeper").classList.remove("selected");
       document.getElementById("place_destroyer").classList.add("selected");
       document.getElementById("place_battleship").classList.remove("selected");
       registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
       shipType = "BATTLESHIP";
       document.getElementById("place_minesweeper").classList.remove("selected");
       document.getElementById("place_destroyer").classList.remove("selected");
       document.getElementById("place_battleship").classList.add("selected");
       registerCellListener(place(4));
    });
    sendXhr("GET", "/game", {}, "", function(data) {
        game = data;
    });
};