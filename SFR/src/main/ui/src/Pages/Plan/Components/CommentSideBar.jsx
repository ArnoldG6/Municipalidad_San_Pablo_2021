import React, { Component } from 'react';
import { Nav } from "react-bootstrap";
import './CommentSideBar.css';
import { Button, Accordion } from "react-bootstrap";
class CommentSideBar extends Component {
    constructor(props){
        super(props);
    }
    
    render() {
        if(!this.props.mobile)
        {
        return (
            <div className="plan-sidebar flex-column">
                <h2 className="text-center mt-3">
                    Comentarios
                </h2>
                <hr />
                <Nav className="navbar navbar-default" role="navigation">
                    <div className="side-menu-container">
                    <Accordion className='mt-2'>

                                        <Accordion.Item>
                                            <Accordion.Header>
                                                "Juan Valdez"
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID:
                                                    Nombre: 
                                                    Fecha:
                                                    Causa:
                                                    Afectacion:
                                                    Descripcion:
                                                    Riesgo Asociado:
                                                </p>
                                                <Button>
                                                    Remover Incidencia
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                              
                            </Accordion>
                    </div>
                </Nav>
            </div>
        );
    }else{
        return (
            <div className="flex-column">
                <h2 className="text-center mt-3">
                    Comentarios
                </h2>
                <hr />
                <Nav className="navbar navbar-default" role="navigation">
                    <div className="side-menu-container">
                    <Accordion className='mt-2'>

                                        <Accordion.Item>
                                            <Accordion.Header>
                                                "Juan Valdez"
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID:
                                                    Nombre: 
                                                    Fecha:
                                                    Causa:
                                                    Afectacion:
                                                    Descripcion:
                                                    Riesgo Asociado:
                                                </p>
                                                <Button>
                                                    Remover Incidencia
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                              
                            </Accordion>
                    </div>
                </Nav>
            </div>
        );
    }
    }
};
export default CommentSideBar;
