// login.js
document.addEventListener('DOMContentLoaded', function() {
	const form = document.getElementById('loginForm');
	const loginBtn = document.getElementById('loginBtn');
	const emailInput = document.getElementById('email');
	const passwordInput = document.getElementById('password');

	// Validación del formulario
	form.addEventListener('submit', function(e) {
		e.preventDefault();

		const email = emailInput.value.trim();
		const password = passwordInput.value.trim();

		// Validar campos requeridos
		if (!email || !password) {
			showError('Por favor, completa todos los campos.');
			return;
		}

		// Validar formato de email
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(email)) {
			showError('Por favor, ingresa un email válido.');
			return;
		}

		// Mostrar estado de carga
		setLoadingState(true);

		// Enviar formulario después de validación
		setTimeout(() => {
			form.submit();
		}, 1000);
	});

	// Función para mostrar errores
	function showError(message) {
		// Crear o actualizar mensaje de error
		let errorDiv = document.querySelector('.error-message');
		if (!errorDiv) {
			errorDiv = document.createElement('div');
			errorDiv.className = 'error-message';
			form.parentNode.insertBefore(errorDiv, form);
		}
		errorDiv.textContent = message;
		errorDiv.style.display = 'block';

		// Auto-ocultar después de 5 segundos
		setTimeout(() => {
			errorDiv.style.display = 'none';
		}, 5000);

		// Hacer scroll al mensaje
		errorDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
	}

	// Función para estado de carga
	function setLoadingState(loading) {
		if (loading) {
			loginBtn.innerHTML = '⏳ Iniciando sesión...';
			loginBtn.disabled = true;
			form.classList.add('loading');
		} else {
			loginBtn.innerHTML = '🚀 Iniciar Sesión';
			loginBtn.disabled = false;
			form.classList.remove('loading');
		}
	}

	// Validación en tiempo real
	emailInput.addEventListener('blur', validateEmail);
	passwordInput.addEventListener('blur', validatePassword);

	function validateEmail() {
		const email = emailInput.value.trim();
		if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
			emailInput.style.borderColor = '#F44336';
		} else if (email) {
			emailInput.style.borderColor = '#4CAF50';
		}
	}

	function validatePassword() {
		const password = passwordInput.value.trim();
		if (password && password.length < 6) {
			passwordInput.style.borderColor = '#F44336';
		} else if (password) {
			passwordInput.style.borderColor = '#4CAF50';
		}
	}

	// Rellenar credenciales de demo (para testing)
	function fillDemoCredentials() {
		emailInput.value = 'juan@email.com';
		passwordInput.value = 'password123';
		validateEmail();
		validatePassword();
	}

	// Opcional: Botón para rellenar credenciales demo (quitar en producción)
	const demoBtn = document.createElement('button');
	demoBtn.type = 'button';
	demoBtn.textContent = 'Rellenar credenciales demo';
	demoBtn.className = 'demo-btn';
	demoBtn.style.cssText = `
        background: #FFC107;
        color: #333;
        border: none;
        padding: 10px;
        border-radius: 5px;
        margin-top: 10px;
        cursor: pointer;
        width: 100%;
        font-size: 0.9em;
    `;
	demoBtn.addEventListener('click', fillDemoCredentials);
	form.appendChild(demoBtn);

	// Manejar mensajes flash del servidor
	const successMessage = document.querySelector('.success-message');
	const errorMessage = document.querySelector('.error-message');

	if (successMessage || errorMessage) {
		window.scrollTo({ top: 0, behavior: 'smooth' });

		// Auto-ocultar mensajes después de 5 segundos
		setTimeout(() => {
			if (successMessage) {
				successMessage.style.display = 'none';
			}
			if (errorMessage) {
				errorMessage.style.display = 'none';
			}
		}, 5000);
	}

	// Limpiar formulario si viene de logout
	if (window.location.search.includes('logout')) {
		emailInput.value = '';
		passwordInput.value = '';
	}
});

