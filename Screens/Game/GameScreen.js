import React from "react";
import { FlatList, View } from "react-native";
import Expo from "expo";
import Theme from "../../Theme/Theme";
import ControlBox from "./ControlBox";
import Puzzle from "./Puzzle";
import GameModel from "../../Model/GameModel";

export default class GameScreen extends React.Component {
    constructor(props) {
        super(props);
        this.model = new GameModel();
        const loop = i => {
            if (i < 10) {
                this.model.addEquation(() => {
                    this.forceUpdate();
                    setTimeout(loop.bind(this, i + 1), 100);
                });
            }
        };
        loop(0);
    }

    render() {
        return (
            <View style={{
                "flex": 1,
                "backgroundColor": Theme.background,
                "paddingTop": Expo.Constants.statusBarHeight
            }}>
                <FlatList data={this.model.equations.map((val, i) => {
                    return {
                        "key": i,
                        "value": val
                    };
                })}
                        renderItem={it => (
                            <Puzzle equation={it.item.value} />
                        )} style={{
                            "flex": 1
                        }} />
                <ControlBox />
            </View>
        );
    }
}
