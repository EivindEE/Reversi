package com.eivind.reversi.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a 8x8 Reversi board
 * @author Eivind Eidheim Elseth
 *
 */
public class Bitboard {
	
	public final static int BLACK = 0;
	public final static int EMPTY = -1;
	public final static int WHITE = 1;
	
	private final static Long INITIAL_POSITION_BLACK = 68853694464L;
	private final static Long INITIAL_POSITION_WHITE = 34628173824L;
	
	private final static Long LEFT_MASK = -9187201950435737472L;
	private final static Long RIGHT_MASK = 72340172838076673L;

	public static void main(String[] args) {
		Bitboard board = new Bitboard();
		Coordinate e6 = new Coordinate(4, 5);
		Coordinate d6 = new Coordinate(3, 5);
		Coordinate c4 = new Coordinate(2, 3);
		Coordinate d3 = new Coordinate(3, 2);
		Coordinate c6 = new Coordinate(2, 5);
		Coordinate f6 = new Coordinate(5, 5);
		Coordinate f4 = new Coordinate(5, 3);
		Coordinate b6 = new Coordinate(1, 5);
		System.out.println(board);
		
		board.makeMove(BLACK, e6);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(WHITE, d6);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(BLACK, c4);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(WHITE, d3);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(BLACK, c6);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(WHITE, f6);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(BLACK, f4);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
		board.makeMove(WHITE, b6);
		System.out.println("Black score: " + board.getScore(BLACK) + ", White score: " + board.getScore(WHITE));
		System.out.println(board);
		
	}
	
	private Long[] pieces;

	/**
	 * Constructs a board with pieces in the starting positions of reversi
	 */
	public Bitboard(){
		this(INITIAL_POSITION_BLACK, INITIAL_POSITION_WHITE);
	}

	/**
	 * Constructs a board with pieces in the positions given by blackPieces and whitePieces
	 */
	public Bitboard(Long blackPieces, Long whitePieces){
		this(new Long[]{blackPieces, whitePieces});
	}
	/**
	 * Constructs a board with pieces in the positions given by the array
	 */
	public Bitboard(Long[] pieces){
		if(pieces.length != 2)
			throw new IllegalArgumentException("Number of players must be 2 but was " + pieces.length);
		this.pieces = pieces;
	}

	/**
	 * @return the blackPieces
	 */
	public Long blackPieces() {
		return pieces[BLACK];
	}
	
	/**
	 * Returns true if the player with the given index has legal moves
	 * Should be called with either the static BLACK or WHITE
	 * @param color
	 * @return
	 */
	public boolean hasLegalMoves(int color) {
		return getLegalMoves(color).size() > 0;
	}
	
	/**
	 * Returns true if the tile at c is empty
	 * @param c
	 * @return
	 */
	public boolean isEmpty(Coordinate c){
		Long position = getLong(c);
		return (position & emptyBoard()) == position;
	}
	/**
	 * Returns true if none of the players have legal moves
	 * @return
	 */
	public boolean isGameComplete(){
		return !hasLegalMoves(BLACK) && !hasLegalMoves(WHITE);
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
	
	public int getScore(int color){
		int score = 0;
		Long remainingPieces = new Long(pieces[color]);
		while(remainingPieces != 0L){
		 score++;
		 remainingPieces ^= Long.highestOneBit(remainingPieces);
		}
		return score;
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
		for(int i = 0; i < white.length() && i < black.length(); i++)
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
				return  new Coordinate((7 - (counter % 8)), counter / 8);
			}
			L = L << 1;
			counter++;
		}
		// If the long does not map to a coordinate on the board.
		throw new IllegalArgumentException("Long " + position + "does not map to a coordinate on the BitBoard");
	}

	private List<Coordinate> getEndPointDown(int color, Long startPoint) {
 		Long potentialEndPoint = shiftDown(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftDown(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}

	private List<Coordinate> getEndPointDownLeft(int color,Long startPoint) {
		Long potentialEndPoint = shiftDownLeft(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftDownLeft(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}
	
	private List<Coordinate> getEndPointDownRight(int color,Long startPoint) {
		Long potentialEndPoint = shiftDownRight(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftDownRight(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}

	private List<Coordinate> getEndPointLeft(int color,Long startPoint) {
 		Long potentialEndPoint = shiftLeft(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftLeft(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}
	
	private List<Coordinate> getEndPointRight(int color,Long startPoint) {
		Long potentialEndPoint = shiftRight(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftRight(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}

	private List<Coordinate> getEndPoints(int color, Coordinate coordinate) {
		ArrayList<Coordinate> endPoints = new ArrayList<Coordinate>();
		Long startPoint = getLong(coordinate);

		// Adds the potential endpoints from all possible directions
		endPoints.addAll(getEndPointUpLeft(color,startPoint));
		endPoints.addAll(getEndPointUp(color,startPoint));
		endPoints.addAll(getEndPointUpRight(color,startPoint));
		endPoints.addAll(getEndPointLeft(color,startPoint));
		endPoints.addAll(getEndPointRight(color,startPoint));
		endPoints.addAll(getEndPointDownLeft(color,startPoint));
		endPoints.addAll(getEndPointDown(color,startPoint));
		endPoints.addAll(getEndPointDownRight(color,startPoint));

		return endPoints;
	}
	private List<Coordinate> getEndPointUp(int color, Long startPoint) {
 		Long potentialEndPoint = shiftUp(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftUp(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}
	
	private List<Coordinate> getEndPointUpLeft(int color,Long startPoint) {
		Long potentialEndPoint = shiftUpLeft(startPoint);
		while(potentialEndPoint != 0){	
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftUpLeft(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}
	private List<Coordinate> getEndPointUpRight(int color, Long startPoint) {
 		Long potentialEndPoint = shiftUpRight(startPoint);
		while(potentialEndPoint != 0){
			if(validEndPoint(color,potentialEndPoint)){
				return longToCoordinateList(potentialEndPoint);
			}
			potentialEndPoint = shiftUpRight(potentialEndPoint);
		}
		return new LinkedList<Coordinate>();
	}


	private List<Coordinate> getLegalMoves(int player) {
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
	
	private Long getLong(Coordinate c){
		long l = 1L;
		l = l << 7 - c.getX();
		l = l << (8*c.getY());
		return l;
	}

	private LinkedList<Coordinate> getLongCoordinates(Long position){
		Long workingPosition = new Long(position);
		LinkedList<Coordinate> coordinates = new LinkedList<Coordinate>();
		long highestOneBit = Long.highestOneBit(workingPosition);
		while(highestOneBit != 0){
			coordinates.add(getCoordinate(highestOneBit));
			workingPosition ^= highestOneBit;
			highestOneBit = Long.highestOneBit(workingPosition);
		}
		return coordinates;
	}


	private boolean isLegalMove(int color, Coordinate coordinate) {
		for(Coordinate c : getLegalMoves(color)){
			if(c.equals(coordinate))
				return true;
		}
		return false;
	}
	private LinkedList<Coordinate> longToCoordinateList(Long l) {
		LinkedList<Coordinate> coordinateList = new LinkedList<Coordinate>();
		coordinateList.add(getCoordinate(l));
		return coordinateList;
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
	
	private void makeMove(int color, Coordinate coordinate) {

		if(isLegalMove(color, coordinate)){
			Collection<Coordinate> piecesToTurn = new ArrayList<Coordinate>();
			piecesToTurn.add(coordinate);
			Collection<Coordinate> endPoints = getEndPoints(color, coordinate);
			piecesToTurn.addAll(endPoints);
			for(Coordinate c : endPoints)
				piecesToTurn.addAll(coordinate.between(c));
			for(Coordinate c : piecesToTurn){
				setPieceAtPosition(color, getLong(c));
			}
		}	
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
	
	private void setPieceAtPosition(int color, Long position){
		this.pieces[1-color] &= ~position; 
		this.pieces[color] = this.pieces[color] | position; 
	}
	
	private Long shiftDown(Long position){
		return new Long(position >> 8);
	}
	
	private Long shiftDownLeft(Long position){
		Long dlShift = position >> 7;
		return new Long(dlShift & ~RIGHT_MASK);
	}
	
	private Long shiftDownRight(Long position){
		Long drShift = position >> 9;
		return new Long(drShift & ~LEFT_MASK);
	}
	
	private Long shiftLeft(Long position){
		Long lShift = position << 1;
		return new Long(lShift & ~RIGHT_MASK);
	}
	
	private Long shiftRight(Long position){
		Long rShift = position >> 1;
		return new Long(rShift & ~LEFT_MASK);
	}

	private Long shiftUp(Long position){
		return new Long(position << 8);
	}
	
	
	private Long shiftUpLeft(Long position){
		Long ulShift = position << 9;
		return new Long(ulShift & ~RIGHT_MASK);
	}	
	
	private Long shiftUpRight(Long position){
		Long urShift = position << 7;
		return new Long(urShift & ~LEFT_MASK);
	}

	private boolean validEndPoint(int color, Long potentialEndPoint) {
		return (potentialEndPoint & pieces[color]) == potentialEndPoint;
	}
}
