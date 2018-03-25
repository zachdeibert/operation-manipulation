import React from "react";
import { Text, View } from "react-native";
import Theme from "../../Theme/Theme";

export default class Operator extends React.Component {
    render() {
        return (
            <View style={{
                "width": 48,
                "height": 48,
                "backgroundColor": Theme.foreground,
                "margin": 5,
                "borderRadius": 5,
                "alignItems": "center",
                "justifyContent": "center"
            }}>
                <Text style={{
                    "fontSize": 24,
                    "color": Theme.background
                }}>{this.props.symbol}</Text>
            </View>
        );
    }
}
