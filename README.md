# 📚Library Database Project📚

### Members: Julian, Anson, Emmanuel, Alex

### Teams:
#### Backend: Julian and Alex
#### Frontend: Anson and Emmanuel

### Frontend
#### User interface (once they login)
- Let them query the book database (book table)
  - Display the book information on one line
  - Display the isbn, book name, and genre
- Have them display account information. Have an account button
- Display account info (button)
  - Show the same thing as the employee interface
  - Add an option to update password?
    - To use the update clause in SQL

#### Employee interface (once they successfully login)
- Let them add books (book table)
  - Include genre, isbn book, book name, library id (somehow get from the user employee?), if its available (drop down menu?)
    - Library card number will be null, and holds will be zero 
- Copy the user interface to query the book database
- Display account info (button) 
    - Their employee id and name (employee table)
    - library they work at (gonna need a query to get the library table to get the library name because we probably don’t want to show the library id)

#### Additional notes:
- We should try to come up with a better way to open new windows and close old ones
    - https://gist.github.com/jewelsea/6460130 haven’t doven to much into this one but it could be promising 
- Find a way to auto set the width and height
