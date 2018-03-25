import React from "react";
import { Text, View } from "react-native";
import Operator from "./Operator";

export default class ControlBox extends React.Component {
    render() {
        return (
            <View style={{
                "minHeight": 48,
                "flexDirection": "row",
                "flexWrap": "wrap",
                "justifyContent": "center",
                "padding": 5
            }}>
                <Operator symbol="+" />
                <Operator symbol="-" />
                <Operator symbol="x" />
                <Operator symbol="/" />
            </View>
        );
    }
}
