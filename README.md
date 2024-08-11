# Tic Tac Toe

Tic-Tac-Toe is a classic two-player game played on a 3x3 grid. The objective for each player, who takes turns marking the spaces with their symbols (X or O), is to form a line of three of their symbols horizontally, vertically, or diagonally. Players aim to block their opponent while attempting to create a winning line. If the grid is filled without any player forming such a line, the game ends in a draw.

## Problem Description

The Tic-Tac-Toe problem involves players making optimal moves to either win or force a draw. The Minimax algorithm is employed to achieve this, considering both the player (MAX) and opponent (MIN) moves to decide the best course of action.

## Problem Formulation

### States

The state of the game is represented by the current configuration of the 3x3 grid.

### Initial State

The game starts with an empty 3x3 grid.

### Successor Function

Possible actions involve placing an X or O in an empty cell of the grid. Players alternate turns, with MAX trying to maximize their chances of winning and MIN trying to minimize MAX's chances.

### Goal Test

The game ends when a player successfully places three of their symbols in a row, column, or diagonal, or when the grid is full, resulting in a draw.

### Path Cost

Each move in the game has a cost of 1.

### Solution

A sequence of moves leading from the initial empty grid to a winning configuration for MAX or MIN, or a draw if neither player can force a win.

## Solving the Problem

The problem can be solved using the Minimax algorithm, which recursively explores all possible game states, evaluating each based on potential future moves to determine the best possible move for the current player.

## Note:

- there is app_photo folder

- this work done by yousef sharbi and anas karakra
