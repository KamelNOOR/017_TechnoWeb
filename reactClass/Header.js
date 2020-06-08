import React,{Component} from 'react';
import './Header.css';
import AutoCompletedText from './AutoCompletedText';
import logo from './bird.png';
import axios from 'axios';

class Header extends Component{
	constructor(props){
		super(props);
		this.state = {
			
    }
    this.logOut = this.logOut.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  logOut(){  
    const self = this;
    axios.get('http://localhost:8080/Birdy/Logout?nickname=' + this.props.id + '&pwd=' + this.props.pwd   )
    .then(function (response) {
    console.log(response.data);
    if(response.data.Status === "OK"){
      self.handleChange();
  }
  if(response.data.Message === "Erreur user not connected"){
    self.handleChange();
}
    })
    .catch(function (error) { 
        // handle error
        console.log(error);
    })
    .finally(function () {
        // always executed
    });

} handleChange() {
  this.props.onConnectedChange();  }
        
	render(){
    const isConnected = this.props.isConnected; 
		return(
			<div id="top">
                <div id="logo"><img src={logo} alt="logo" /> </div>
                <div id="search"> 
                <AutoCompletedText />
                </div>
                
                <div id="connect"> 
                 <input id="logout" type="button" className="btn" value="Logout" onClick={this.logOut} />
                </div>
            </div>
		);
	}
}
export default Header;