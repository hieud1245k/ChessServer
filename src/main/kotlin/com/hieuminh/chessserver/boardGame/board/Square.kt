package com.hieuminh.chessserver.boardGame.board

import com.hieuminh.chessserver.boardGame.pieces.PieceColor
import com.hieuminh.chessserver.entities.BoxEntity
import java.io.Serializable
import java.util.stream.Collectors

data class Square(val arrayPosition: Int) : Serializable, Comparable<Square> {
    val rank = arrayPosition / 8
    val file = arrayPosition % 8

    fun nextRank(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank - 1, file)
        } else {
            square(rank + 1, file)
        }
    }

    fun next2Rank(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank - 2, file)
        } else {
            square(rank + 2, file)
        }
    }

    fun next2File(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank, file + 2)
        } else {
            square(rank, file - 2)
        }
    }

    fun previous2File(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank, file - 2)
        } else {
            square(rank, file + 2)
        }
    }

    fun nextRankNextFile(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank - 1, file + 1)
        } else {
            square(rank + 1, file - 1)
        }
    }

    fun nextRankPreviousFile(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank - 1, file - 1)
        } else {
            square(rank + 1, file + 1)
        }
    }

    fun previousFile(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank, file - 1)
        } else {
            square(rank, file + 1)
        }
    }

    fun nextFile(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank, file + 1)
        } else {
            square(rank, file - 1)
        }
    }

    fun previousRank(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank + 1, file)
        } else {
            square(rank - 1, file)
        }
    }

    fun previous2Rank(pieceColor: PieceColor): Square {
        return if (pieceColor == PieceColor.WhiteSet) {
            square(rank + 2, file)
        } else {
            square(rank - 2, file)
        }
    }

    fun exists(): Boolean {
        return rank in 0..7 && file in 0..7
    }

    fun diagonalSquares(): List<Square> {
        val acc: MutableList<Square> = ArrayList()
        for (distance in 1..7) {
            acc.add(square(rank + distance, file + distance))
            acc.add(square(rank + distance, file - distance))
            acc.add(square(rank - distance, file + distance))
            acc.add(square(rank - distance, file - distance))
        }
        return acc.stream().filter { obj: Square -> obj.exists() }.collect(Collectors.toList())
    }

    fun straightSquares(): List<Square> {
        val acc: MutableList<Square> = ArrayList()
        for (distance in 1..7) {
            acc.add(square(rank + distance, file))
            acc.add(square(rank - distance, file))
            acc.add(square(rank, file + distance))
            acc.add(square(rank, file - distance))
        }
        return acc.stream().filter { obj: Square -> obj.exists() }.collect(Collectors.toList())
    }

    fun toPosition(): Pair<Int, Int> {
        val x = Move.FILE_NAMES[file].first().code - 97
        val y = Move.RANK_NAMES[rank].first().code - 49
        return Pair(x, y)
    }

    fun toBoxEntity() = BoxEntity().apply {
        val position = toPosition()
        setPosition(position)
    }

    override fun toString(): String {
        return Move.FILE_NAMES[file] + Move.RANK_NAMES[rank]
    }

    /**
     * position of the square in a sequence from 0 to 63
     */
    fun arrayPosition(): Int {
        return rank * 8 + file
    }

    override fun compareTo(other: Square): Int {
        return arrayPosition().compareTo(other.arrayPosition())
    }
}
