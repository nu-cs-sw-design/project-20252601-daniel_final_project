# Use Cases

## UC1: Start the Game

**Actor:** Player

**Precondition:** The application is launched and no game is currently in progress.

**Basic Flow:**

1. The system displays language selection options (English/Korean).
2. The user selects a language.
3. The system confirms language selection and displays the game mode options (Exploding Kittens, Imploding Kittens, Streaking Kittens).
4. The user selects a game mode.
5. The system confirms game mode and prompts for the number of players (2-4).
6. The user enters number of players.
7. The system deals 1 Defuse card to each player.
8. The system initializes the deck based on game mode.
9. The system shuffles the deck.
10. The system deals 5 cards to each player from the deck.
11. The system inserts Exploding Kittens into the deck (numberOfPlayers - 1 cards, or numberOfPlayers for Streaking Kittens).
12. If Imploding Kittens mode, the system inserts 1 Imploding Kitten into the deck.
13. The system shuffles the deck once more.
14. The system begins the game loop with Player 0's turn.

**Exception Flow A:**

2.a. If user enters invalid language choice, system displays error message. Resume at Step 1.

**Exception Flow B:**

6.a. If user enters an invalid number of players, system displays error message. Resume at Step 5.

**Postcondition:** Game is initialized with all players having cards, deck is shuffled, and first player's turn begins.

---

## UC2: Draw Exploding Kitten Without Defuse

**Actor:** Player

**Precondition:** It is the player's turn and they choose to end their turn by drawing a card. The top card of the deck is an Exploding Kitten. The player does not have a Defuse card in their hand.

**Basic Flow:**

1. The player chooses to end their turn and draw a card.
2. The system draws the top card from the deck.
3. The system identifies the card as an Exploding Kitten.
4. The system displays "You drew an Exploding Kitten!"
5. The system checks the player's hand for a Defuse card.
6. The system determines the player has no Defuse card.
7. The system displays "You don't have a Defuse card. You exploded!"
8. The system marks the player as eliminated.
9. The system removes the Exploding Kitten from play.
10. The system advances to the next alive player's turn.

**Postcondition:** The player is eliminated from the game. The game continues with remaining players.

---

## UC3: Draw Exploding Kitten With Defuse

**Actor:** Player

**Precondition:** It is the player's turn and they choose to end their turn by drawing a card. The top card of the deck is an Exploding Kitten. The player has a Defuse card in their hand.

**Basic Flow:**

1. The player chooses to end their turn and draw a card.
2. The system draws the top card from the deck.
3. The system identifies the card as an Exploding Kitten.
4. The system displays "You drew an Exploding Kitten!"
5. The system checks the player's hand for a Defuse card.
6. The system determines the player has a Defuse card.
7. The system displays "You have a Defuse card! You defused the Exploding Kitten."
8. The system removes the Defuse card from the player's hand.
9. The system prompts the player for an insertion index (0 to deck size).
10. The player enters an index.
11. The system inserts the Exploding Kitten at the specified position in the deck.
12. The system ends the player's turn and advances to the next player.

**Exception Flow A:**

10.a. If user enters an invalid index (negative or greater than deck size), system displays error message. Resume at Step 9.

**Postcondition:** The player survives. The Defuse card is consumed. The Exploding Kitten is placed back in the deck at the player's chosen position.

---

## UC4: Play Nope Card (Simple Nope)

**Actor:** Player B (responding to Player A's action)

**Precondition:** Player A has played a card that can be Noped (e.g., Shuffle). Player B has a Nope card in their hand.

**Basic Flow:**

1. Player A plays a card (e.g., Shuffle).
2. The system prompts all other players if they want to play a Nope card.
3. Player B chooses to play their Nope card.
4. The system removes the Nope card from Player B's hand.
5. The system prompts remaining players if they want to Nope the Nope.
6. No other players choose to play a Nope.
7. The system cancels Player A's original action.
8. The system displays "Nope! Action cancelled."
9. The system discards Player A's original card.
10. Player A's turn continues normally.

**Exception Flow A:**

3.a. No players choose to play a Nope card. The system executes Player A's original action. End use case.

**Postcondition:** Player A's action is cancelled. Player A's card and Player B's Nope card are both discarded. Player A's turn continues.

---

## UC5: Nope Chain

**Actor:** Multiple Players with Nope cards

**Precondition:** Player A has played a card that can be Noped. Multiple players have Nope cards.

**Basic Flow:**

1. Player A plays Shuffle card.
2. The system prompts all other players if they want to play a Nope card.
3. Player B plays Nope (blocks Shuffle).
4. The system prompts all players if they want to Nope the Nope.
5. Player C plays Nope (blocks Player B's Nope, allowing Shuffle).
6. The system prompts all players if they want to Nope the Nope.
7. Player D plays Nope (blocks Player C's Nope, blocking Shuffle).
8. The system prompts all players if they want to Nope the Nope.
9. No more players choose to play Nope.
10. The system counts total Nopes played (3 - odd number).
11. The system determines the original action is blocked.
12. The system discards all played cards (Shuffle + 3 Nopes).
13. Player A's turn continues normally.

**Postcondition:** Original action is blocked (odd number of Nopes). All played Nope cards and the original card are discarded.

**Special Requirements:**

1. Odd number of Nopes = action is blocked.
2. Even number of Nopes = action proceeds.

---

## UC6: Play Shuffle Card

**Actor:** Player

**Precondition:** It is the player's turn. The player has a Shuffle card in their hand.

**Basic Flow:**

1. The player selects the Shuffle card from their hand.
2. The system removes the Shuffle card from the player's hand.
3. The system prompts all other players if they want to play a Nope card.
4. No players choose to Nope.
5. The system prompts the player for number of shuffles (1-100).
6. The player enters a valid number.
7. The system shuffles the deck the specified number of times using Fisher-Yates algorithm.
8. The system displays "Deck has been shuffled."
9. The player's turn continues (they may play more cards or draw to end turn).

**Exception Flow A:**

4.a. A player chooses to Nope the Shuffle. The Shuffle is cancelled (see UC4 or UC5). End use case.

**Exception Flow B:**

6.a. The player enters an invalid number (≤0 or >100). The system displays an error message. Resume at Step 5.

**Postcondition:** The deck is randomized. The Shuffle card is discarded. The player's turn continues.

---

## UC7: Play Defuse Card (After Drawing Exploding Kitten)

**Actor:** Player

**Precondition:** The player has drawn an Exploding Kitten. The player has a Defuse card in their hand.

**Basic Flow:**

1. The system detects player drew an Exploding Kitten.
2. The system checks player's hand and finds a Defuse card.
3. The system automatically uses the Defuse card.
4. The system removes the Defuse card from the player's hand.
5. The system prompts the player for insertion index (0 to deck size).
6. The player enters a valid index.
7. The system inserts the Exploding Kitten at the specified position.
8. The system displays "Exploding Kitten defused and placed back in deck."
9. The player's turn ends and play passes to the next player.

**Exception Flow A:**

6.a. The player enters an invalid index. The system displays an error message. Resume at Step 5.

**Postcondition:** The player survives. The Defuse card is consumed. The Exploding Kitten is back in the deck at the chosen position. Turn passes to next player.

**Special Requirements:**

1. Defuse cannot be Noped.
2. Player can strategically place Exploding Kitten (top = index 0 for next player, deep = safer for everyone).

---

## UC8: Strategic Defuse Placement (Target Next Player)

**Actor:** Player

**Precondition:** The player has drawn an Exploding Kitten and has a Defuse card. The player wants to eliminate the next player.

**Basic Flow:**

1. The player draws an Exploding Kitten.
2. The system prompts to use Defuse.
3. The player's Defuse card is consumed.
4. The system prompts for insertion index.
5. The player enters 0 (top of deck).
6. The system places the Exploding Kitten on top of the deck.
7. Turn passes to the next player.
8. The next player must draw at the end of their turn.
9. The next player draws the Exploding Kitten.

**Postcondition:** The Exploding Kitten is placed on top of the deck, likely eliminating the next player (unless they also have a Defuse).