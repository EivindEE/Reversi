package com.eivind.reversi.game;


import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * This class represents a 8x8 Reversi board
 * @author Eivind Eidheim Elseth
 *
 */
public class Bitboard {
	
	public final static int BLACK = 0;
	public final static int WHITE = 1;
	private final static Long INITIAL_POSITION_BLACK = 68853694464L;
	private final static Long INITIAL_POSITION_WHITE = 34628173824L;
	
	private final static Long LEFT_MASK = -9187201950435737472L;
	private final static Long RIGHT_MASK = 72340172838076673L;

	public static void main(String[] args) {
		Bitboard board = new Bitboard();
		Coordinate d6 = new Coordinate(3, 5);
		System.out.println("d6:" + d6);
		board.makeMove(BLACK,d6);
		System.out.println(board);
	
		
	}
	
	private void makeMove(int color, Coordinate coordinate) {

		if(isLegalMove(color, coordinate)){
			ArrayList<Coordinate> piecesToTurn = new ArrayList<Coordinate>();
			piecesToTurn.add(coordinate);
			System.out.println("Size before getEndPoints: " + piecesToTurn.size());
			ArrayList<Coordinate> endPoints = getEndPoints(color, coordinate);
			
			piecesToTurn.addAll(endPoints);
			System.out.println("Size after getEndPoints: " + piecesToTurn.size());
			for(Coordinate c : endPoints)
				piecesToTurn.addAll(coordinate.between(c));
			for(Coordinate c : piecesToTurn){
				setPieceAtPosition(color, getLongFromCoordinate(c));
			}
		}
		
	}

	private ArrayList<Coordinate> getEndPoints(int color, Coordinate coordinate) {
		int otherColor = 1 - color;
		ArrayList<Coordinate> endPoints = new ArrayList<Coordinate>();
		Long potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftUpRight(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftUp(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftUpLeft(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftLeft(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftRight(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & emptyBoard()) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftDownLeft(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & pieces[color]) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftDown(potentialEndPoint);
		}
		potentialEndPoint = getLongFromCoordinate(coordinate);
		while(potentialEndPoint != 0){
			if((potentialEndPoint & pieces[color]) == potentialEndPoint){
				endPoints.add(getCoordinate(potentialEndPoint));
				break;
			}
			potentialEndPoint = shiftRight(potentialEndPoint);
		}
		return endPoints;
	}

	private boolean isLegalMove(int color, Coordinate coordinate) {
		for(Coordinate c : getLegalMoves(color)){
			if(c.equals(coordinate))
				return true;
		}
		return false;
	}

	private Long[] pieces;
	
	public Bitboard(){
		this(INITIAL_POSITION_BLACK, INITIAL_POSITION_WHITE);
	}

	public Bitboard(Long blackPieces, Long whitePieces){
		pieces = new Long[]{blackPieces, whitePieces};
	}

	private void setPieceAtPosition(int color, Long position){
		this.pieces[1-color] &= ~position; 
		this.pieces[color] = this.pieces[color] | position; 
	}

	/**
	 * @return the blackPieces
	 */
	public Long blackPieces() {
		return pieces[BLACK];
	}
	
	public boolean isEmpty(){
		return pieces[BLACK] == 0 && pieces[WHITE] == 0;
	}

	/**
	 * @param blackPieces the blackPieces to set
	 */
	public void setBlackPieces(Long blackPieces) {
		this.pieces[BLACK] = blackPieces;
	}
	
	/**
	 * @param whitePieces the whitePieces to set
	 */
	public void setWhitePieces(Long whitePieces) {
		this.pieces[WHITE] = whitePieces;
	}

	public String toString(){
		String s;
		String white = longToString(whitePieces());
		String black = longToString(blackPieces());
		String both = combineBoards(white, black);
		s = displaySideBySide(white, black, both);
		return s;
	}
	/**
	 * @return the whitePieces
	 */
	public Long whitePieces() {
		return pieces[WHITE];
	}
	
	private String combineBoards(String white, String black) {
		StringBuilder s = new StringBuilder(white.length());
		for(int i = 0; i < white.length(); i++)
			if(white.charAt(i)==' ')
				s.append(' ');
			else if(white.charAt(i)=='1' && black.charAt(i)=='1')
				s.append('X');
			else if(white.charAt(i)=='1')
				s.append('w');
			else if(black.charAt(i)=='1')
				s.append('b');
			else
				s.append('.');
		
		return s.toString();
	}
	private String displaySideBySide(String white, String black, String both) {
		String s = "";
		int lineLength = 16;
		int beginIndex = 0;
		int endIndex = lineLength;
		while(endIndex <= black.length() || endIndex <= white.length()){
			s += white.substring(beginIndex, endIndex) + '\t' 
			  + black.substring(beginIndex, endIndex)+ '\t' 
			  + both.substring(beginIndex, endIndex) + '\n';
			beginIndex = endIndex;
			endIndex += lineLength;
		}
		return s;
	}


	private Long emptyBoard() {
		return new Long(~blackPieces() & ~whitePieces());
	}
	
	private String formatSingleLong(Long l){
		String boardString = longToString(l);
		String s = "";
		int lineLength = 16;
		int beginIndex = 0;
		int endIndex = lineLength;
		while(endIndex <= boardString.length()){
			s += boardString.substring(beginIndex, endIndex) + '\n';
			beginIndex = endIndex;
			endIndex += lineLength;
		}
		return s; 
	}

	private Coordinate getCoordinate(Long position){
		Coordinate theCoordinate = null;
		Long L = 1L;
		int counter = 0;
		while(theCoordinate == null && counter < 64){
			if(L.equals(position)){
				theCoordinate = new Coordinate(counter % 8, counter / 8);
				break;
			}
			L = L << 1;
			counter++;
		}
		if(theCoordinate == null)
			throw new InvalidParameterException("Long " + position + "does not map to a coordinate on the BitBoard");
		return theCoordinate;	
	}


	private ArrayList<Coordinate> getLegalMoves(int player) {
		ArrayList<Coordinate> legalMoves = new ArrayList<Coordinate>();
		legalMoves.addAll(movesUpLeft(player));
		legalMoves.addAll(movesUp(player));
		legalMoves.addAll(movesUpRight(player));
		legalMoves.addAll(movesLeft(player));
		legalMoves.addAll(movesRight(player));
		legalMoves.addAll(movesDownLeft(player));
		legalMoves.addAll(movesDown(player));
		legalMoves.addAll(movesDownRight(player));
		return legalMoves;
		
	}
	private ArrayList<Coordinate> getLongCoordinates(Long position){
		Long workingPosition = new Long(position);
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		long highestOneBit = Long.highestOneBit(workingPosition);
		while(highestOneBit != 0){
			coordinates.add(getCoordinate(highestOneBit));
			workingPosition ^= highestOneBit;
			highestOneBit = Long.highestOneBit(workingPosition);
		}
		return coordinates;
	}

	private String longToString(Long l){
		StringBuilder s = new StringBuilder(128);
		int leadingZeros = Long.numberOfLeadingZeros(l);
		for(int i = 0; i < leadingZeros; i++){
			s.append("0 ");
		}
		if(s.length() == 64)
			return s.toString();
		String binString = Long.toBinaryString(l);
		for(int i = 0; i < binString.length(); i++){
			s.append(binString.charAt(i) + " ");
		}
		return s.toString();
	}
	
	private ArrayList<Coordinate> movesDown(int player) {
		ArrayList<Coordinate> downMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftDown(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftDown(potentialMoves) & emptyBoard;
			downMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftDown(potentialMoves) & pieces[otherPlayer];
		}
		return downMoves;
	}


	private ArrayList<Coordinate> movesDownLeft(int player) {
		ArrayList<Coordinate> downLeftMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftDownLeft(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftDownLeft(potentialMoves) & emptyBoard;
			downLeftMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftDownLeft(potentialMoves) & pieces[otherPlayer];
		}
		return downLeftMoves;
	}


	private ArrayList<Coordinate> movesDownRight(int player) {
		ArrayList<Coordinate> downRightMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftDownRight(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftDownRight(potentialMoves) & emptyBoard;
			downRightMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftDownRight(potentialMoves) & pieces[otherPlayer];
		}
		return downRightMoves;
	}

	private ArrayList<Coordinate> movesLeft(int player) {
		ArrayList<Coordinate> leftMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftLeft(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftLeft(potentialMoves) & emptyBoard;
			leftMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftLeft(potentialMoves) & pieces[otherPlayer];
		}
		return leftMoves;
	}

	private ArrayList<Coordinate> movesRight(int player) {
		ArrayList<Coordinate> rightMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftRight(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftRight(potentialMoves) & emptyBoard;
			rightMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftRight(potentialMoves) & pieces[otherPlayer];
		}
		return rightMoves;
	}

	private ArrayList<Coordinate> movesUp(int player) {
		ArrayList<Coordinate> upMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftUp(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftUp(potentialMoves) & emptyBoard;
			upMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftUp(potentialMoves) & pieces[otherPlayer];
		}
		return upMoves;
	}
	
	private ArrayList<Coordinate> movesUpLeft(int player) {
		ArrayList<Coordinate> upLeftMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftUpLeft(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftUpLeft(potentialMoves) & emptyBoard;
			upLeftMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftUpLeft(potentialMoves) & pieces[otherPlayer];
		}
		return upLeftMoves;
	}
	
	private ArrayList<Coordinate> movesUpRight(int player) {
		ArrayList<Coordinate> upRightMoves = new ArrayList<Coordinate>(); 
		int otherPlayer = 1 - player;
		Long potentialMoves = shiftUpRight(pieces[player]) & pieces[otherPlayer];
		Long emptyBoard = emptyBoard();
		while(potentialMoves != 0){
			Long legalMoves = shiftUpRight(potentialMoves) & emptyBoard;
			upRightMoves.addAll(getLongCoordinates(legalMoves));
			potentialMoves = shiftUpRight(potentialMoves) & pieces[otherPlayer];
		}
		return upRightMoves;
	}
	
	private Long shiftDown(Long position){
		return new Long(position >> 8);
	}
	
	private Long shiftDownLeft(Long position){
		Long rShift = position >> 7;
		return new Long(rShift & ~RIGHT_MASK);
	}
	
	private Long shiftDownRight(Long position){
		Long rShift = position >> 9;
		return new Long(rShift & ~LEFT_MASK);
	}
	
	private Long shiftLeft(Long position){
		Long rShift = position << 1;
		return new Long(rShift & ~RIGHT_MASK);
	}
	
	private Long shiftRight(Long position){
		Long rShift = position >> 1;
		return new Long(rShift & ~LEFT_MASK);
	}
	
	private Long shiftUp(Long position){
		Long shifted = new Long(position);
		return new Long(position << 8);
	}
	
	private Long shiftUpLeft(Long position){
		Long rShift = position << 9;
		return new Long(rShift & ~RIGHT_MASK);
	}

	private Long shiftUpRight(Long position){
		Long rShift = position << 7;
		return new Long(rShift & ~LEFT_MASK);
	}
	
	
	private Long getLongFromCoordinate(Coordinate c){
		int x = c.getX();
		int y = c.getY();
		int position = x + (8 * y);
		return 1L << position;
	}
	
	private Coordinate getCoordinateFromLong(Long position){
		Coordinate theCoordinate = null;
		Long L = 1L;
		int counter = 0;
		while(theCoordinate == null && counter < 64){
			if(L.equals(position)){
				theCoordinate = new Coordinate(counter % 8, counter / 8);
				break;
			}
			L = L << 1;
			counter++;
		}
		if(theCoordinate == null)
			throw new InvalidParameterException("Long " + position + "does not map to a coordinate on the BitBoard");
		return theCoordinate;	
	}
}
