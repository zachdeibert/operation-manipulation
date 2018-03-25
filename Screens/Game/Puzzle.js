import React from "react";
import { Text, View } from "react-native";
import Theme from "../../Theme/Theme";
import Symbol from "./Symbol";

export default class Puzzle extends React.Component {
    constructor(props) {
        super(props);
        this.handleLayout = this.handleLayout.bind(this);
        this.state = {
            "width": 0,
            "height": 0
        };
    }

    handleLayout(ev) {
        this.setState({
            "width": ev.nativeEvent.layout.width,
            "height": ev.nativeEvent.layout.height
        });
    }

    render() {
        return (
            <View style={{
                "backgroundColor": Theme.puzzle.unsolved,
                "height": 96,
                "borderBottomColor": Theme.background,
                "borderBottomWidth": 1,
                "flexDirection": "row",
                "alignItems": "center",
                "opacity": this.state.width > 0 && this.state.height > 0 ? 1 : 0
            }} onLayout={this.handleLayout}>
                {this.props.equation.symbols.map((symbol, i) => (
                    <Symbol key={`symbol-${i}`} symbol={symbol} width={this.state.width} height={this.state.height} />
                ))}
            </View>
        );
    }
}
