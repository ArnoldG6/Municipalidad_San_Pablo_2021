import React, { Component } from 'react';
import { Nav } from "react-bootstrap";
import './CommentSideBar.css';
import { Button, Accordion } from "react-bootstrap";
import AddCommentModal  from './AddCommentModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';
export default class CommentSideBar extends Component {
    constructor(props){
        super(props);
        this.state = {
            comments: [],
            show: false,
            showDel: false,
            delID: "",
            planID: ""
        }
        this.removeComment = this.removeComment.bind(this)
        this.openModalAddComment = this.openModalAddComment.bind(this);
        this.closeModalAddComment = this.closeModalAddComment.bind(this);
        this.openModalDelComment = this.openModalDelComment.bind(this);
        this.closeModalDelComment = this.closeModalDelComment.bind(this);
    }
    removeComment() {
        this.props.removeComment(this.state.delID);
        this.closeModalDelComment();
    }
    openModalAddComment() {
        this.setState({ show: true });
    };

    closeModalAddComment() {
        this.setState({ show: false });
    };

    openModalDelComment(id, pkID) {
        console.log(pkID);
        this.setState({ showDel: true, delID: id, planID: pkID});
    };

    closeModalDelComment() {
        this.setState({ showDel: false});
    };

    render() {
        return (
            <div className="plan-sidebar flex-column">
                <h2 className="text-center mt-3">
                    Comentarios
                </h2>
                <hr />
                <Button
                    size="sm"
                    onClick={this.openModalAddComment}
                >
                    Agregar comentario
                </Button>
                <Nav className="navbar navbar-default" role="navigation">
                {(typeof  this.props.plan === 'undefined' ||  this.props.plan=== null) ? <h1>Cargando...</h1> :
                          this.props.plan.commentList.length === 0 ? <h1>No se han agregado comentarios</h1> :
                            <Accordion className='mt-2'>
                                { (this.props.plan.commentList).map((comentario) => {
                                    return (
                                        <Accordion.Item eventKey={comentario.pkID} key={comentario.pkID}>
                                            <Accordion.Header>
                                                {comentario.author}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    Nombre: {comentario.author} <br />
                                                    Fecha: {comentario.entryDate} <br />
                                                    Comentario: {comentario.comment} <br />
                                                    URL: {comentario.url} <br />
                                                </p>
                                                <Button
                                                    variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-danger" : "outline-dark"}
                                                    disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                                    onClick={() => this.openModalDelComment(comentario.pkID, this.props.plan.pkID)}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover comentario
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })}
                            </Accordion>
                    }
                </Nav>
                <AddCommentModal
                    show={this.state.show} 
                    plan={this.props.plan}
                    refreshPage={this.props.refreshPage}
                    closeModal={this.closeModalAddComment} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelComment}
                    action={this.removeComment}
                    header={"Eliminar un comentario de un Plan"}
                    body={"¿Esta seguro que desea eliminar este comentario del Plan?"} />
            </div>
        );
    }
};
