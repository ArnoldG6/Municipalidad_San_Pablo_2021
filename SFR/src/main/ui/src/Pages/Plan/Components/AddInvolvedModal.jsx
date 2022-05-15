import React, { Component } from 'react';
import '../Plan.css';
import { Modal, Button, Table, Form, ListGroup } from "react-bootstrap";
import 'react-toastify/dist/ReactToastify.css';
import Pages from '../../../SharedComponents/Pagination/Pages';

class AddInvolvedModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            userIDs: [],
            userView: [],
            currentPage: 1,
            pageItemAmount: 10
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.updatePage = this.updatePage.bind(this);
        this.handleUserRender = this.handleUserRender.bind(this);
        this.updatePageItems = this.updatePageItems.bind(this);
    }

    componentDidUpdate(prevProps) {
        if (this.props.users !== prevProps.users) {
            this.handleUserRender();
        }
    }

    componentDidMount() {
        this.handleUserRender();
    }

    handleSubmit() {
        this.setState({ userIDs: [] });
        this.props.closeModal();
        this.props.addInvolved(this.state.userIDs);
    }

    handleSelect(id) {
        let list = this.state.userIDs;
        if (list.includes(id)) {
            list = list.filter(function (value) { return value !== id });
            this.setState({ userIDs: list });
        } else {
            list.push(id);
            this.setState({ userIDs: list });
        }
    }

    /* Pagination */
    updatePage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        }, () => {
            this.handleUserRender();
        });
    }

    updatePageItems(amount) {
        this.setState({
            pageItemAmount: amount,
            currentPage: 1
        }, () => {
            this.handleUserRender();
        })
    }

    handleUserRender() {
        let items = [];
        let itemAmount = this.state.pageItemAmount;
        let pos = (this.state.currentPage - 1) * itemAmount;
        for (let i = 0; i < itemAmount; i++) {
            let item = this.props.users.at(pos);
            if (typeof item !== 'undefined' && item !== null) {
                items.push(item);
            }
            pos++;
        }
        this.setState({ userView: items });
    }

    render() {
        let show = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <div>
                <Modal show={show} onHide={() => { this.setState({ userIDs: [] }); closeModal(); }}>
                    <Modal.Header closeButton>
                        Seleccione los Usuarios que desea agregar al Plan
                    </Modal.Header>
                    <Modal.Body >
                        {(typeof this.props.users === 'undefined' || this.props.users === null) ? <h1>No se han agregado otros Usuarios</h1> :
                            this.props.users.length === 0 ? <h1>No hay Usuarios disponibles</h1> :
                                <div>
                                    <Table>
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.state.userView.map((user) => {
                                                return (
                                                    <tr key={user.idUser}>
                                                        <td className="align-middle">
                                                            <h3><Form.Check
                                                                aria-label="Seleccionar Usuario"
                                                                name="selectUser"
                                                                value={user.idUser}
                                                                checked={this.state.userIDs.find(e => e === user.idUser)}
                                                                onClick={() => { this.handleSelect(user.idUser) }} /></h3>
                                                        </td>
                                                        <td>
                                                            <ListGroup>
                                                                <ListGroup.Item eventKey={user.idUser}>
                                                                    <div className="ms-2 me-auto">
                                                                        <div className="fw-bold">{user.official.name} {user.official.surname}</div>
                                                                        {user.official.department.description} <br />
                                                                    </div>
                                                                </ListGroup.Item>
                                                            </ListGroup>
                                                        </td>
                                                    </tr>
                                                )
                                            })}
                                        </tbody>
                                    </Table>
                                    <Pages
                                        listLength={this.props.users.length}
                                        itemAmount={this.state.pageItemAmount}
                                        updatePage={this.updatePage}
                                        currentPage={this.state.currentPage}
                                        updatePageItems={this.updatePageItems} />

                                </div>
                        }

                        <div className="text-center">
                            <Button className='btn-sfr' onClick={this.handleSubmit}
                                disabled={((typeof this.props.users === 'undefined' || this.props.users === null) ? true :
                                    this.props.users.length === 0 ? true : false) || this.state.userIDs.length === 0}>
                                Guardar
                            </Button>
                        </div>
                    </Modal.Body>
                </Modal>
            </div>
        );
    };
};
export default AddInvolvedModal;