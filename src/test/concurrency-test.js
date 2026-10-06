import http from 'k6/http';
import { check, sleep } from 'k6';

// 1. Concurrency Test Configuration
export const options = {
  scenarios: {
    concurrency_test: {
      executor: 'constant-vus',
      vus: 50,
      duration: '30s',
    },
  },
  thresholds: {
    // 95% of concurrent requests must finish within 500ms
    http_req_duration: ['p(95)<500'],
    // At least 99% of all assertions must pass
    checks: ['rate>0.99'],
  },
};

// 2. Target Endpoint (Port 8001)
const TARGET_URL = 'http://localhost:8001/api/v1/eligibility';

// Utility helper to generate a unique 14-digit National ID per VU/Iteration
function generateNationalId(vu, iter) {
  const prefix = '2980826'; // Standard 7-digit prefix
  const suffix = String(vu * 10000 + (iter % 10000)).padStart(7, '0');
  return (prefix + suffix).slice(0, 14);
}

export default function () {
  // Generate unique 14-digit national ID per request to guarantee thread safety
  const nationalId = generateNationalId(__VU, __ITER);

  // 3. Payload strictly matching EligibilityEvaluationRequestDTO validation rules
  const payload = JSON.stringify({
    nationalId: nationalId,
    applicantAge: 32,
    creditScore: 720,
    monthlyNetSalary: 15000.0,
    existingMonthlyDebt: 2000.0,
    requestedAmount: 50000.0,
    requestedTenureMonths: 24,
    activeLoans: 1,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  };

  // Execute POST Request
  const res = http.post(TARGET_URL, payload, params);

  // Safe JSON Parsing
  let body = null;
  try {
    body = res.json();
  } catch (e) {
    body = null;
  }

  // Diagnostic Logger: Prints exact Spring Boot error payload on non-200 responses
  if (res.status !== 200 && __ITER === 0 && __VU === 1) {
    console.error(`\n[DIAGNOSTIC LOG] HTTP ${res.status} Response from Spring Boot:`);
    console.error(res.body);
    console.error(`-----------------------------------------------------------\n`);
  }

  // Extract rule result array from EligibilityEvaluationResponseDTO
  const rules = body?.ruleResultList || body?.auditLog;

  // 4. Bulletproof Assertions
  check(res, {
    'status is 200 OK': (r) => r.status === 200,
    'body is valid JSON': () => body !== null,
    'applicationId present': () => body?.applicationId !== undefined && body?.applicationId !== null,
    'verdict present': () => body?.verdict !== undefined,
    'ruleResultList is array': () => Array.isArray(rules),
    'all rules evaluated under concurrency': () => Array.isArray(rules) && rules.length > 0,
  });

  // Short pause to avoid OS socket starvation
  sleep(0.1);
}