# The News Feed Final Project

## Heroku link

https://news-feed-iba-tech.herokuapp.com 

## Test user for Heroku App

Email: buttercupppg2020@gmail.com
Password: password

## Technologies used

Spring Boot, Hibernate, Jsoup, Spring Email, Spring Security, Thymeleaf, PostgreSql

## Note

If you want to run locally, uncomment bean in autorun so that news are loaded to db. Don't forget to change db credentials in application.yaml. 
Let`s say, you searched for specific news and want to return back to the stage where you were able to see all articles from sites that are enabled , press IBA Tech icon, it acts like 'reload' button.

## Presentation

https://onedrive.live.com/view.aspx?resid=E5CC93A2C81549D8!240&ithint=file%2cpptx&authkey=!ALb4k_qhkzR7fCE

## Content:

The Front-End part is done using HTML, CSS and JS only. No frameworks, no hard stuff.

### Pages:

- Login
- Registration
- Forgot password 
- Main Page
- The article page
- My news

### Files:

- Images
- CSS Files
- HTML Files

## The idea of the project

The main idea of the project is to build a News feed-like application that collects news from all popular sources with an option to disable news from specific sources. 

## How everything should work: 

The algorithm that a new user should follow consists of the following:

1) Open the login page, choose the `Create account` option
2) Get redirected to the `Sign Up` page, fill in the form with an existing email and press the `Create account` button
3) Receive an email with a link
4) Get redirected to the login page, enter the newly created credentials and press `login`
5) Get redirected to the `Main Page`, where the user can see all the news from various sources. The user can click on an article to open it and read it. The user can also search for specific articles by keyword and date
6) In order to disable news from specific sources, the user should hover onto his profile in the top right corner and choose the `My news` option. The user will be redirected to the `My news` page, where he can disable news from a specific source by clicking the `On+/Off-` button. He can also search for a specific author by using the `Search` input box next to the `Disable news from specific websites` header.

