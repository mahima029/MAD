import 'package:flutter/material.dart';
import 'diet_plan_screen.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Diet Planner")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Spacer(),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (_) => DietPlanScreen(dietType: "Veg")),
                );
              },
              child: Text("Vegetarian Diet"),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (_) => DietPlanScreen(dietType: "Non-Veg")),
                );
              },
              child: Text("Non-Vegetarian Diet"),
            ),
            Spacer(),
          ],
        ),
      ),
    );
  }
}