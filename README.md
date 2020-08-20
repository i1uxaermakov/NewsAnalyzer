# NewsAnalyzer

NewsAnalyzer project allows to see what words dominate the titles of the articles within a specific time frame in the politics section of major news outlets. This repository is for the backend code of the project.

A deployed version of the project is available at https://peaceful-kalam-af1ea1.netlify.app/about

The data from news outlets is collected using Python web-scraping scrypt. The data is then saved into a database; it can be accessed using an API or can be presented on a React-powered website (link above).


### Sample run
On the main page, users can select the time interval they want to get information about. 
![First page](https://github.com/i1uxaermakov/NewsAnalyzer/blob/master/readme_pictures/Picture1.png)

After selecting the time interval and clicking *Generate*, the words most commonly found among the articles are displayed. **The greater the number of times the word appears, the bigger the font.** The words are cut at 6 characters to account for different forms of the same word. Stopwords (that, this, he/she, and, or, etc) have been excluded.
![Second page](https://github.com/i1uxaermakov/NewsAnalyzer/blob/master/readme_pictures/Picture2.png)


### Other parts of the project
Python Web Scraper GitHub repository:
https://github.com/Alexandr5656/WebScraper

Frontend (React App) GitHub repository:
https://github.com/hagoraya/HYO-Frontend
