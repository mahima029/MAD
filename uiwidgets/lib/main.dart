//Create UI widgets
//import the material design package
import 'package:flutter/material.dart';
//Entry point in application
void main(){
  //To run the application using class name
  runApp(const MyApp());
}

//To call the widget as stateless
class MyApp extends StatelessWidget {
  const MyApp({super.key});
//To create the button UI
/*  @override
  Widget build(BuildContext context) {
    //Create the button on Material App
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: SafeArea(
          child: Align(
            alignment: Alignment.topCenter,
               child: Padding(padding:
               const EdgeInsets.only(top: 20),
               child: ElevatedButton(onPressed: (){
                 print("Login button clicked");
               },
                   child: const Text("Login")),
               ),
            ),
          ),
        ),
      );
  }*/

//To create input text field for "Enter user name".
/*@override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      home: Scaffold(
        body: Padding(
            padding: const EdgeInsets.all(20),
                child: TextField(
                  decoration: InputDecoration(
                    labelText: 'Enter your name',
                    border: OutlineInputBorder(),
                  ),
                ),
        ),
      ),
    );
  }*/

/*@override
  Widget build(BuildContext context) {
    // TODO: implement build
  return MaterialApp(
    home: Scaffold(
      body: Center(
        child: Text('This is my first flutter application'),
      ),
    ),
  );
  }*/

//Create list in flutter
@override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      home: Scaffold(
        body: ListView(
          children: const [
            ListTile(
              title: Text('Varun Sharma')),
            ListTile(
              title: Text('Virat Garg')),
            ListTile(
                title: Text('Vinayak Rastogi')),
          ],
        ),
      ),
    );
  }
}
