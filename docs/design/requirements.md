# Use Cases

## UC1: Start Game

**Actor:** Players (2-4 sharing terminal)

**Precondition:**

- Application is runnable on device
- No game is currently running

**Basic Flow:**

1. System starts and displays language selection menu (English/Korean)
2. User selects preferred language by entering 1 or 2
3. System displays game variant selection menu (Exploding Kittens/Imploding Kittens/Streaking Kittens)
4. User selects game variant by entering 1, 2, or 3
5. System displays player count prompt (2-4 players)
6. User enters number of players
7. System initializes game state:
   - Deals one Defuse card to each player
   - Creates deck with cards appropriate to selected variant
   - Shuffles the deck
   - Deals 5 cards to each player
   - Inserts Exploding Kitten cards (count = players - 1 for base game, players for Streaking Kittens)
   - Inserts Imploding Kitten if Imploding Kittens variant selected
   - Shuffles the deck again
8. System displays initial game state showing Player 0's hand and turn information
9. System begins turn loop until only one player survives

**Exception Flow A (Invalid Language):**

2.a. If user enters invalid input (not 1 or 2), system displays error message. Resume at Step 1.

**Exception Flow B (Invalid Variant):**

4.a. If user enters invalid input (not 1, 2, or 3), system displays error message. Resume at Step 3.

**Exception Flow C (Invalid Player Count):**

6.a. If user enters invalid player count (not 2-4), system displays error message. Resume at Step 5.

**Postcondition:**

- Game is initialized with selected variant and player count
- Each player has 6 cards (1 Defuse + 5 random cards)
- Deck contains appropriate number of Exploding Kittens
- Player 0's turn begins

**Special Requirements:**

- Language selection persists for entire game session
- Game variant determines deck composition
- All players share the same terminal for input

---

## UC2: Play Shuffle Card

**Actor:** Current player

**Precondition:**

- Game is in progress
- Current player has at least one SHUFFLE card in hand
- It is current player's turn

**Trigger:** Player chooses to play SHUFFLE card from hand

**Basic Flow:**

1. System displays current player's hand with card indices
2. Player selects SHUFFLE card index from their hand
3. System removes SHUFFLE card from player's hand
4. System checks if any other player wants to play NOPE card:
   - For each other player who has NOPE:
   - System prompts: "Player X has a NOPE card. Would you like to play a NOPE? (1=Yes, 2=No)"
   - Player responds
5. If no NOPE played (or even number of NOPEs), system proceeds:
   - System prompts: "How many times to shuffle? (1-100)"
   - Player enters number of shuffles
   - System validates input (must be 1-100)
   - System executes shuffle using Fisher-Yates algorithm
   - System displays confirmation message
6. Player continues turn (may play more cards or end turn by drawing)

**Alternate Flow A (NOPE Chain - Odd NOPEs):**

4.a. One or more players play NOPE cards (odd total count). System displays cancellation message. SHUFFLE effect does not execute. SHUFFLE card is still removed from hand. Resume normal turn.

**Alternate Flow B (NOPE Chain - Even NOPEs):**

4.a. Multiple players play NOPE cards (even total count). System displays that effect goes through. Resume at Step 5.

**Exception Flow A (Invalid Shuffle Count):**

5.a. If player enters invalid number (≤0 or >100), system displays error. Resume at Step 5.

**Postcondition:**

- If not NOPEd: Deck is shuffled, card order randomized
- If NOPEd: Deck remains unchanged
- SHUFFLE card removed from player's hand
- Player may continue turn or end turn

---

## UC3: Play Nope Card

**Actor:** Any player with NOPE card (except the one who played the card being NOPEd)

**Precondition:**

- Game is in progress
- Another player has just played a NOPEable card (SHUFFLE, ATTACK, etc.)
- Reacting player has at least one NOPE card in hand
- Reacting player is not the one who played the card being NOPEd

**Trigger:** System prompts player to decide whether to play NOPE

**Basic Flow:**

1. Another player plays a NOPEable card effect
2. System identifies all players with NOPE cards (excluding card player)
3. For each eligible player in turn order:
   - System displays: "Player X has a NOPE card"
   - System prompts: "Would you like to play a NOPE? (1=Yes, 2=No)"
   - Player responds with 1 (Yes) or 2 (No)
4. If player chooses Yes (plays NOPE):
   - System removes NOPE card from player's hand
   - System displays that player played NOPE
   - System recursively checks remaining players for NOPE response (NOPE the NOPE)
5. If player chooses No:
   - System displays that player did not play NOPE
   - Continue to next player with NOPE card
6. When all players have responded:
   - If total NOPE count is odd: Original effect is CANCELLED
   - If total NOPE count is even (including 0): Original effect EXECUTES

**Alternate Flow A (NOPE a NOPE):**

4.a. After a NOPE is played, system checks if other players want to NOPE the NOPE. Same flow continues recursively. Each NOPE negates the previous action. Final result depends on odd/even count.

**Exception Flow A (Invalid Input):**

3.a. If player enters invalid input (not 1 or 2), system displays error message. Resume at Step 3.

**Postcondition:**

- NOPE card(s) removed from player(s) hands
- Original effect either cancelled (odd NOPEs) or executes (even NOPEs)
- Game continues with original card player's turn

**Special Requirements:**

- Odd number of NOPEs = action is blocked
- Even number of NOPEs = action proceeds
- NOPE cannot cancel Exploding Kitten or Defuse

---

## UC4: Draw Exploding Kitten Card

**Actor:** Current player

**Precondition:**

- Game is in progress
- Current player's turn is ending
- At least one Exploding Kitten exists in the deck
- Current player must draw a card to end their turn

**Trigger:** Player ends turn and draws top card from deck

**Basic Flow:**

1. Player completes their card plays and chooses to end turn
2. System draws top card from deck
3. System identifies card as EXPLODING_KITTEN type
4. System displays: "You drew an Exploding Kitten!"
5. System checks if player has DEFUSE card in hand:
   - If no DEFUSE: Go to Exception Flow A (Player Explodes)
   - If has DEFUSE: Continue to Step 6
6. System displays that player has a Defuse card
7. System removes DEFUSE card from player's hand
8. System prompts player for insertion index (0 to deck size)
9. Player enters valid index
10. System inserts EXPLODING_KITTEN back into deck at specified position
11. System displays confirmation message
12. Turn passes to next player

**Exception Flow A (Player Explodes - No Defuse):**

5.a. System displays: "You don't have a Defuse card!"
5.b. System displays: "You exploded!"
5.c. System marks player as DEAD
5.d. System removes player from active rotation
5.e. Turn passes to next alive player
5.f. Game checks for victory condition (only 1 player remaining)

**Exception Flow B (Invalid Insertion Position):**

9.a. If player enters position < 0 or > deck size, system displays error message. Resume at Step 8.

**Postcondition:**

- If survived: EXPLODING_KITTEN reinserted into deck at chosen position, DEFUSE removed from hand
- If exploded: Player marked as DEAD, removed from game
- Turn advances to next player
- Game ends if only 1 player remains alive

---

## UC5: Play Defuse Card

**Actor:** Current player who drew an Exploding Kitten

**Precondition:**

- Player has just drawn an Exploding Kitten card
- Player has at least one DEFUSE card in hand

**Trigger:** System automatically triggers Defuse when Exploding Kitten is drawn and player has Defuse

**Basic Flow:**

1. System detects player drew an Exploding Kitten
2. System checks player's hand and finds a DEFUSE card
3. System displays that player will use Defuse to survive
4. System removes the DEFUSE card from player's hand
5. System prompts player for insertion index:
   - "Where do you want to insert the Exploding Kitten? (0 to [deck size])"
   - Index 0 = top of deck (next player draws it)
   - Index = deck size = bottom of deck (safest position)
6. Player enters desired index
7. System validates index (must be 0 to deck size inclusive)
8. System inserts the Exploding Kitten at specified position in deck
9. System displays confirmation: "Exploding Kitten defused and placed back in deck"
10. Player's turn ends and play passes to next player

**Exception Flow A (Invalid Index):**

6.a. If player enters invalid index (negative or greater than deck size), system displays error message. Resume at Step 5.

**Postcondition:**

- Player survives the Exploding Kitten
- DEFUSE card is removed from player's hand (consumed)
- EXPLODING_KITTEN is placed back into deck at player's chosen position
- Turn passes to next player

**Special Requirements:**

- Defuse cannot be NOPEd
- Player can strategically place Exploding Kitten:
  - Index 0 (top) = next player will likely draw it
  - Higher index = deeper in deck, safer for everyone
- Defuse is automatically used when Exploding Kitten is drawn (not optional)
