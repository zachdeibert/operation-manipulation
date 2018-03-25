import React from "react";
import { Button, Text, View } from "react-native";
import Theme from "../../Theme/Theme";

export default class HomeScreen extends React.Component {
    render() {
        return (
            <View style={{
                "flex": 1,
                "backgroundColor": Theme.background,
                "justifyContent": "center",
                "padding": 10
            }}>
                <Text style={{
                    "color": Theme.title,
                    "fontSize": 48,
                    "textAlign": "center",
                    "marginBottom": 80
                }}>Operation Manipulation</Text>
                <View style={{
                    "marginVertical": 10
                }}>
                    <Button title="Play" color={Theme.button}
                            onPress={this.props.navigation.navigate.bind(this.props.navigation, "Game")}  />
                </View>
                <View style={{
                    "marginVertical": 10
                }}>
                    <Button title="Options" color={Theme.button}
                            onPress={this.props.navigation.navigate.bind(this.props.navigation, "Options")} />
                </View>
            </View>
        );
    }
}
