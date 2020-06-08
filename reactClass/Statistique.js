import React,{Component} from 'react';
import './Statistique.css';

class Statistique extends Component{
	constructor(props){
		super(props);
		this.state = {
			
		}
    }
        
	render(){
		return(
			<div id="connected">
         <dl>
          <dt> Connecté</dt>
            <dd> Kamel </dd>
            <dd> Jacqueline </dd>
            <dd> Henry </dd>
            <dd> Daniel </dd>
            <dd> Sherezade </dd>
            <dd> Melda </dd>
            <dd> Eve </dd>
            <dd> Clement </dd>
        </dl>       
      </div>
		);
	}
}
export default Statistique;