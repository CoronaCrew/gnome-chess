UPDATE pgn
SET event = ?,
    site = ?,
    date = ?,
    time = ?,
    round = ?,
    white = ?,
    black = ?,
    result = ?,
    annotator = ?,
    timeControl = ?,
    whiteTimeLeft = ?,
    blackTimeLeft = ?,
    clockType = ?,
    timerIncrement = ?,
    setUp = ?,
    fen = ?,
    termination = ?,
    whiteAi = ?,
    whiteLevel = ?,
    blackAi = ?,
    blackLevel = ?
WHERE id = ?
