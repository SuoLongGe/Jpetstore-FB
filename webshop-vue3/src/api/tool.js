import axios from "@/axios";

export function GET(api, data) {
    return axios.get(api, data)
}

export function POST(api, data) {
    return axios.post(api, data)
}

export function PUT(api, data) {
    return axios.put(api, data)
}

export function PATCH(api, data) {
    return axios.patch(api, data)
}

export function DELETE(api, data) {
    return axios.delete(api, data)
}
