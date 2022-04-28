import React, { Component } from 'react';
import { Card, Nav } from "react-bootstrap";
import './CommentSideBar.css';
import { Button } from "react-bootstrap";
import AddCommentModal from './AddCommentModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';
export default class CommentSideBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            comments: [],
            show: false,
            showDel: false,
            delID: "",
            planID: ""
        }
        this.handleRemove = this.handleRemove.bind(this)
        this.openModalAddComment = this.openModalAddComment.bind(this);
        this.closeModalAddComment = this.closeModalAddComment.bind(this);
        this.openModalDelComment = this.openModalDelComment.bind(this);
        this.closeModalDelComment = this.closeModalDelComment.bind(this);
    }

    handleRemove() {
        this.props.removeComment(this.state.delID);
        this.closeModalDelComment();
    }
    openModalAddComment() {
        this.setState({ show: true });
    };

    closeModalAddComment() {
        this.setState({ show: false });
    };

    openModalDelComment(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelComment() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div className='comment-container'>
                <h2 className="text-center mt-3">
                    Comentarios
                </h2>
                <Button
                    variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "success" : "outline-dark"}
                    disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                    size="sm"
                    onClick={this.openModalAddComment}
                >
                    <i className="bi bi-plus-square"></i> {' '}
                    Agregar comentario
                </Button>
                <Nav className="navbar navbar-default" role="navigation">
                    {(typeof this.props.plan === 'undefined' || this.props.plan === null) ? <h1>Cargando...</h1> :
                        this.props.plan.commentList.length === 0 ? <h4>No se han agregado comentarios</h4> :
                            <div className='mt-2'>
                                {(this.props.plan.commentList).map((comentario) => {
                                    return (
                                        <Card key={comentario.pkID} >
                                            <div className="card-text-body">
                                                <Card.Body className="cardClass">
                                                    <Card.Title>
                                                        {comentario.author}
                                                    </Card.Title>
                                                    <Card.Subtitle className="mb-2 text-muted">
                                                        {"Ingresado: " + comentario.entryDate}
                                                    </Card.Subtitle>
                                                    <Card.Text>
                                                        Comentario: {comentario.comment} <br />
                                                    </Card.Text>
                                                    <Button
                                                        variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-danger" : "outline-dark"}
                                                        disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                                        onClick={() => this.openModalDelComment(comentario.pkID)}>
                                                        <i className="bi bi-dash-square-fill"></i>{' '}
                                                        Remover comentario
                                                    </Button>
                                                    {comentario.url !== "" ?
                                                        <Card.Link href={comentario.url}>
                                                            <Button variant="link">
                                                                + Más información
                                                            </Button>
                                                        </Card.Link>
                                                        : null}

                                                </Card.Body>
                                            </div>
                                        </Card>
                                    );
                                })}
                            </div>
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
                    action={this.handleRemove}
                    header={"Eliminar un Comentario de un Plan"}
                    body={"¿Esta seguro que desea eliminar este Comentario del Plan?"} />
            </div>
        );
    }
};
