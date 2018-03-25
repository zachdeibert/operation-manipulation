import React from "react";
import { Text, View } from "react-native";
import Operator from "./Operator";
import Operators from "../../Model/Operators";

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
                {this.props.model.allowedOperators.map((op, i) => (
                    <Operator key={`operator-${i}`} symbol={Operators[op].symbol} />
                ))}
            </View>
        );
    }
}
