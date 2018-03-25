import { StackNavigator } from "react-navigation";
import HomeScreen from "./Screens/Home/HomeScreen";
import GameScreen from "./Screens/Game/GameScreen";
import OptionScreen from "./Screens/Options/OptionScreen";

const App = StackNavigator({
    "Home": {
        "screen": HomeScreen
    },
    "Game": {
        "screen": GameScreen
    },
    "Options": {
        "screen": OptionScreen
    }
}, {
    "initialRouteName": "Home",
    "navigationOptions": {
        "header": null
    }
});
export default App;
