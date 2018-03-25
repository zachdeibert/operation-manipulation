import React from "react";
import { Text } from "react-native";
import ModelSymbol from "../../Model/Symbol";
import Operators from "../../Model/Operators";

export default class Symbol extends React.Component {
    render() {
        return (
            <Text style={{
                "textAlign": "center",
                "fontSize": 36,
                "width": 40,
                "height": 40,
                "position": "absolute",
                "left": this.props.symbol.x * this.props.width - 20,
                "top": this.props.symbol.y * this.props.height - 20
            }}>
                {this.props.symbol.type == ModelSymbol.Type.Constant ? this.props.symbol.value : Operators[this.props.symbol.id].symbol}
            </Text>
        );
    }
}
