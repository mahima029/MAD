import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main(){
  runApp(DietPlannerApp());
}

class DietPlannerApp extends StatelessWidget {
  const DietPlannerApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Diet Planner",
      theme: ThemeData(
        primarySwatch: Colors.green
      ),
      home: HomeScreen(),
    );
  }
}
