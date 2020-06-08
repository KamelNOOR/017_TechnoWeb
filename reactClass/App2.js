import React,{Component} from 'react';
import './App2.css';
import App3 from './App3';

class App2 extends Component{
	constructor(props){
		super(props);
		this.state = {
			languages : [
				{name: "Php", votes: 0},
				{name: "Python", votes: 0},
				{name: "Go", votes: 0},
				{name: "Java", votes: 0}
			],
			isConnected : false,
		}
	}

	vote (i) {
		let newLanguages = [...this.state.languages];
		newLanguages[i].votes++;
		function swap(array, i, j) {
			var temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
        this.setState({languages: newLanguages});
        
		
	}
	
	output(evt) {
        this.setState({isConnected:evt});
    }
    
	render(){
		return(
			<div>
     			{this.state.isConnected ? console.log("nooooonononon") : <App3 date={{isConnected:this.state.isConnected,output:this.output.bind(this)}}/>}    
			</div>
		);
	}
}
export default App2;