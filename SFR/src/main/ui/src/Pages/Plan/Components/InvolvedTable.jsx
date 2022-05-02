import React, { Component } from 'react';
import { Button, ListGroup } from "react-bootstrap";
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';
import AddInvolvedModal from './AddInvolvedModal';

class InvolvedTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            involved: [],
            showUserModal: false,
            showDel: false,
            delID: ""
        };
        this.removeInvolved = this.removeInvolved.bind(this);
        this.openModalAddUser = this.openModalAddUser.bind(this);
        this.closeModalAddUser = this.closeModalAddUser.bind(this);
        this.openModalDelUser = this.openModalDelUser.bind(this);
        this.closeModalDelUser = this.closeModalDelUser.bind(this);
    }

    removeInvolved() {
        this.props.removeInvolved(this.state.delID);
        this.closeModalDelUser();
    }

    openModalAddUser() {
        this.setState({ showUserModal: true });
    };

    closeModalAddUser() {
        this.setState({ showUserModal: false });
    };

    openModalDelUser(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelUser() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div>
                <div className='container-fluid'>
                    <Button
                        size="sm"
                        onClick={this.openModalAddUser}
                        variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "success" : "dark"}
                        disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                        key="AddUserButton">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Involucrado
                    </Button>
                    {(typeof this.props.involved === 'undefined' || this.props.involved === null) ? <h1>No se han agregado involucrados</h1> :
                        this.props.involved.length === 0 ? <h1>No se han agregado involucrados</h1> :
                            <ListGroup className='mt-2'>
                                {this.props.involved.map((user) => {
                                    return (
                                        <ListGroup.Item className="d-flex justify-content-between align-items-start" key={user.idUser}>
                                            <div className="ms-2 me-auto">
                                                <div className="fw-bold">{user.official.name} {user.official.surname}</div>
                                                {user.official.department.description} <br />
                                                <Button variant="link" href={process.env.REACT_APP_PROFILE + "?id=" + user.idUser}>+ Perfil del Usuario</Button>
                                                <Button className="d-lg-none"
                                                    variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") ? "outline-danger" : "outline-dark"}
                                                    disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") ? true : false}
                                                    onClick={() => this.openModalDelUser(user.idUser)}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Involucrado
                                                </Button>
                                            </div>
                                            <Button className="d-none d-lg-block"
                                                variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") ? "outline-danger" : "outline-dark"}
                                                disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") ? true : false}
                                                onClick={() => this.openModalDelUser(user.idUser)}>
                                                <i className="bi bi-dash-square-fill"></i>{' '}
                                                Remover Involucrado
                                            </Button>

                                        </ListGroup.Item>
                                    );
                                })}
                            </ListGroup>
                    }
                </div>
                <AddInvolvedModal
                    show={this.state.showUserModal}
                    closeModal={this.closeModalAddUser}
                    planID={this.props.planID}
                    users={this.props.users}
                    addInvolved={this.props.addInvolved}
                />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelUser}
                    action={this.removeInvolved}
                    header={"Eliminar Involucrado de un Plan"}
                    body={"¿Esta seguro que desea eliminar este Involucrado del Plan?"} />
            </div>
        );
    }
};
export default InvolvedTable;