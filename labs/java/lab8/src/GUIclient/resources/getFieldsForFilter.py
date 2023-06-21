fields = ["y", "creationDate", "salary", "position", "status", "birthday", "height", "passportId", "creatorId"]
for i, field in enumerate(fields, start=3):
    print(f'<Label fx:id="{field}Label" text="{field}" GridPane.rowIndex="{i}"/>')
    print(f'<ComboBox fx:id="{field}ComboBox" prefWidth="150.0" GridPane.columnIndex="1" GridPane.rowIndex="{i}"/>')
    print(f'<TextField fx:id="{field}Field" GridPane.columnIndex="2" GridPane.rowIndex="{i}"/>')
