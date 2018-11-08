var isSetup = true;
var placedShips = 0;
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
             Notify(surrenderText);
           }
        document.getElementById(elementId).rows[attack.location.row].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
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

    for (let i = 0; i < 10; i++) {
        for (let j = 0; j < 10; j++) {
            if(game.playersBoard.squares[i][j].occupied)
                document.getElementById("player").rows[i].cells[j].classList.add("occupied");
        }
    }
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

var _minesweeper = function() {
       shipType = "MINESWEEPER";
       document.getElementById("place_minesweeper").classList.add("selected");
       document.getElementById("place_destroyer").classList.remove("selected");
       document.getElementById("place_battleship").classList.remove("selected");
       registerCellListener(place(2));
}

var _destroyer = function() {
       shipType = "DESTROYER";
       document.getElementById("place_minesweeper").classList.remove("selected");
       document.getElementById("place_destroyer").classList.add("selected");
       document.getElementById("place_battleship").classList.remove("selected");
       registerCellListener(place(3));
}

var _battleship = function() {
       shipType = "BATTLESHIP";
       document.getElementById("place_minesweeper").classList.remove("selected");
       document.getElementById("place_destroyer").classList.remove("selected");
       document.getElementById("place_battleship").classList.add("selected");
       registerCellListener(place(4));
}

function cellClick() {
    let row = this.parentNode.rowIndex;
    let col = String.fromCharCode(this.cellIndex + 65);

    let sweeper = document.getElementById("place_minesweeper");
    let destroyer= document.getElementById("place_destroyer");
    let battleship= document.getElementById("place_battleship");

    let playerCont = document.getElementById('playerShips');
    let oppCont = document.getElementById('shipGraveYard');

    let divVBox = document.getElementById('div_vBox');

    let vBox = document.getElementById('is_vertical');

    console.log(row + " " + col);
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
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, "You can't attack the same square twice", function(data) {
            game = data;
            if (game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].result == "SUNK" || game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].result == "SURRENDER") {
                Notify("You sunk the opponent's " + game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind);
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "minesweeper"){
                    sweeper.classList.remove('selected');
                    sweeper.classList.remove('hidden');
                    sweeper.removeEventListener("click",_minesweeper,true);
                }
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "destroyer"){
                    destroyer.classList.remove('hidden');
                    destroyer.classList.remove('selected');
                    destroyer.removeEventListener("click",_destroyer,true);
                }
                if(game.opponentsBoard.attacks[game.opponentsBoard.attacks.length - 1].ship.kind == "battleship"){
                    battleship.classList.remove('hidden');
                    battleship.classList.remove('selected');
                    battleship.removeEventListener("click",_battleship,true);
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

    document.getElementById("place_minesweeper").addEventListener("click", _minesweeper, true);
    document.getElementById("place_destroyer").addEventListener("click", _destroyer, true);
    document.getElementById("place_battleship").addEventListener("click", _battleship, true);
    sendXhr("GET", "/game", {}, "", function(data) {
        game = data;
    });
};