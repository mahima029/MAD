import 'package:flutter/material.dart';
import '../data/diet_data.dart';

class DietPlanScreen extends StatelessWidget {
  final String dietType;

  DietPlanScreen({required this.dietType});

  @override
  Widget build(BuildContext context) {
    final diet = dietPlans[dietType]!;

    return Scaffold(
      appBar: AppBar(title: Text("$dietType Diet Plan")),
      body: ListView(
        padding: EdgeInsets.all(16),
        children: diet.entries.map((meal) {
          return Card(
            child: ListTile(
              title: Text(meal.key,
                  style: TextStyle(fontWeight: FontWeight.bold)),
              subtitle: Text(meal.value),
            ),
          );
        }).toList(),
      ),
    );
  }
}