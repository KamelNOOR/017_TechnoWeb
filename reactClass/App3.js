import React, { Component } from 'react';
import axios from 'axios';
import './App3.css';
import Header from './Header.js';
import Statistique from './Statistique.js';

class App3 extends Component{
	constructor(props){
		super(props);
		this.state = {
            id : null,
            pwd: null,
            isConnected : false, 
            reponse : false,    
            msg : null,
            listmsg : [],
            listMsgChanged : true,
            
        };
        this.signIn = this.signIn.bind(this);
        this.logIn = this.logIn.bind(this);
        this.addMsg = this.addMsg.bind(this);
        this.getListMsg = this.getListMsg.bind(this);
        this.handleConnectionChange = this.handleConnectionChange.bind(this);
    }
    
    signIn(){
        const self = this;
        axios.get('http://localhost:8080/Birdy/UserCreation?nickname=' + this.state.id + '&pwd=' + this.state.pwd   )
        .then(function (response) {
            console.log(response.data);
             console.log()
        })
        .catch(function (error) {   
            // handle error
            console.log(error);
        })
         .finally(function () {  
            // always executed
        });
    }

    logIn(){  
        const self = this;
        axios.get('http://localhost:8080/Birdy/Login?nickname=' + this.state.id + '&pwd=' + this.state.pwd   )
        .then(function (response) {
        console.log(response.data);
        if(response.data.Status === "OK"){
            self.setState({isConnected : true})
        }
        })
        .catch(function (error) { 
            // handle error
            console.log(error);
        })
        .finally(function () {
            // always executed
        });
    }

    addMsg(){  
        const self = this;
        axios.get('http://localhost:8080/Birdy/AddMessage?user=' + this.state.id + '&msg=' + this.state.msg   )
        .then(function (response) {
        console.log(response.data);
        if(response.data.Status === "OK"){
            console.log(self.state.msg);
            self.setState({listMsgChanged : true})
        }
        })
        .catch(function (error) { 
            // handle error
            console.log(error);
        })
        .finally(function () {
            // always executed
        });
    }

    getListMsg(){  
    
        const self = this;
        axios.get('http://localhost:8080/Birdy/GetMessage?user=' + this.state.id   )
        .then(function (response) {
        console.log(response.data);
        if(response.data.Status === "OK"){
            console.log(response.data.MessageList)
            self.setState({listmsg : response.data.MessageList})
        }
        })
        .catch(function (error) { 
            // handle error
            console.log(error);
        })
        .finally(function () {
            // always executed
        });
    }

    myChangeHandler = (event) => { this.setState({pwd: event.target.value}); }
    myChangeHandler2 = (event) => { this.setState({id: event.target.value}); }
    myChangeHandler3 = (event) => { this.setState({msg: event.target.value}); }

    handleConnectionChange(){
        this.setState({isConnected : false});
    }

    

    render() {
        if(this.state.isConnected === false){ 
            return (
                <div className="App3">
                
                    <div className="login-box">
                        <h1>Create</h1>
                        <div className="textbox">
                            <i className="fas fa-user"></i>
                            <input type="text" placeholder="Username"  onChange={this.myChangeHandler2}/>
                        </div>

                        <div className="textbox">
                            <i className="fas fa-lock"></i>
                            <input type="password" placeholder="Password"  onChange={this.myChangeHandler}/>
                        </div>
                        <input type="button" className="btn" value="Sign in" onClick={this.signIn}/>
                    </div>

                    <div className="login-box2">
                        <h1>Login</h1>
                        <div className="textbox">
                            <i className="fas fa-user"></i>
                            <input type="text" placeholder="Username"  onChange={this.myChangeHandler2}/>
                        </div>

                        <div className="textbox">
                            <i className="fas fa-lock"></i>
                            <input type="password" placeholder="Password"  onChange={this.myChangeHandler}/>
                        </div>
                        <input type="button" className="btn" value="Log in" onClick={this.logIn}/>
                    
                    </div>

                </div>
            );
        } else{
            if(this.state.listMsgChanged === true){
                this.getListMsg()
                this.setState({listMsgChanged : false})
            }
            return(
                <div>
                    <div className="header">
                        <Header 
                        id={this.state.id} 
                        pwd={this.state.pwd} 
                        isConnected={this.state.isConnected}
                        onConnectedChange={this.handleConnectionChange}
                        />
                    </div>
                    <div className="body">
                        <div className="statistique">
                            <h1> Bonjour {this.state.id} !</h1>
                            <Statistique />
                        </div>
                        <div className="message">
                        <input type="text" id="mess" placeholder="New Message" onChange={this.myChangeHandler3}/>
                        <input type="button" className="btn" value="Add New Message" onClick={this.addMsg}/>
                        
                        
                        {this.state.listmsg.map((msg) =>    <li>{msg}</li>  )}
                        </div>
                    </div>
                </div> 
            )
        }
    }
}

export default App3;